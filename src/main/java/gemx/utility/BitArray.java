package gemx.utility;

public final class BitArray implements Cloneable {
    private static final int[] bitCountPatterns;

    static {
        bitCountPatterns = new int[0x100];
        for (int i = 0; i < 0x100; ++i) {
            int bitCount = 0;
            for (int bitIndex = 0; bitIndex < 8; ++bitIndex) {
                if ((i & (1 << bitIndex)) != 0) {
                    ++bitCount;
                }
            }
            bitCountPatterns[i] = bitCount;
        }
    }

    private final byte[] body;
    private final int size;

    public BitArray(int size) {
        assert size >= 0;
        this.size = size;
        this.body = new byte[(this.size + (8 - 1)) / 8];
    }

    public BitArray(int size, boolean initialValue) {
        this(size);
        if (initialValue) {
            for (int i = 0; i < this.body.length; ++i) {
                this.body[i] = (byte) 0xff;
            }
        }
    }

    private static int findMismatchIndex(BitArray bita, boolean[] boola) {
        int size = bita.length();
        int size2 = boola.length;
        assert size == size2;
        for (int i = 0; i < size; ++i) {
            boolean valueBit = bita.getAt(i);
            boolean valueBool = boola[i];
            if (valueBit != valueBool) {
                return i;
            }
        }
        return -1;
    }


    public Object clone() {
        final BitArray c = new BitArray(this.size);
        System.arraycopy(this.body, 0, c.body, 0, this.body.length);
        return c;
    }

    public int length() {
        return this.size;
    }

    public boolean getAt(int index) throws IndexOutOfBoundsException {
        if (!(0 <= index && index < this.size)) {
            throw new IndexOutOfBoundsException();
        }
        final int bytePos = index / 8;
        final int bitPos = index - (bytePos * 8);
        assert 0 <= bytePos && bytePos < this.size;
        assert 0 <= bitPos && bitPos < 8;
        return (this.body[bytePos] & (1 << bitPos)) != 0;
    }

    public int find(boolean value) {
        return find(value, 0);
    }

    public int find(boolean value, int startIndex) throws IndexOutOfBoundsException {
        if (startIndex == this.size) {
            return -1; // not found
        }
        if (!(0 <= startIndex && startIndex < this.size)) {
            throw new IndexOutOfBoundsException();
        }
        boolean found = false;
        int bytePos = startIndex / 8;
        int bitPos = startIndex - bytePos * 8;
        if (value) {
            while (!found) {
                for (; bytePos < this.body.length; ++bytePos) {
                    if (this.body[bytePos] != 0) {
                        break; // for bytePos
                    }
                }
                if (bytePos == this.body.length) {
                    return -1; // not found
                }
                for (; bitPos < 8; ++bitPos) {
                    if ((this.body[bytePos] & (1 << bitPos)) != 0) {
                        found = true;
                        break; // for bitPos
                    }
                }
                if (!found) {
                    ++bytePos;
                    bitPos = 0;
                }
            }
        } else {
            while (!found) {
                for (; bytePos < this.body.length; ++bytePos) {
                    if (this.body[bytePos] != (byte) 0xff) {
                        break; // for bytePos
                    }
                }
                if (bytePos == this.body.length) {
                    return -1; // not found
                }
                for (; bitPos < 8; ++bitPos) {
                    if ((this.body[bytePos] & (1 << bitPos)) == 0) {
                        found = true;
                        break; // for bitPos
                    }
                }
                if (!found) {
                    ++bytePos;
                    bitPos = 0;
                }
            }
        }
        assert bitPos < 8;

        int foundIndex = bytePos * 8 + bitPos;
        if (foundIndex < this.size) {
            return foundIndex;
        }
        return -1; // not found
    }

    public void setAt(int index, boolean value) throws IndexOutOfBoundsException {
        if (!(0 <= index && index < this.size)) {
            throw new IndexOutOfBoundsException();
        }
        final int bytePos = index / 8;
        final int bitPos = index - (bytePos * 8);
        assert 0 <= bytePos && bytePos < this.size;
        assert 0 <= bitPos && bitPos < 8;
        if (value) {
            this.body[bytePos] |= (1 << bitPos);
        } else {
            this.body[bytePos] &= ~(1 << bitPos);
        }
    }

    public void fill(int beginIndex, int endIndex, boolean value) throws IndexOutOfBoundsException {
        if (beginIndex == endIndex) {
            return;
        }

        if (!(0 <= beginIndex && beginIndex <= endIndex && endIndex < this.size)) {
            throw new IndexOutOfBoundsException();
        }

        final int beginBytePos = beginIndex / 8;
        final int beginBitPos = beginIndex - (beginBytePos * 8);
        assert 0 <= beginBytePos && beginBytePos < this.size;
        assert 0 <= beginBitPos && beginBitPos < 8;

        final int endBytePos = endIndex / 8;
        final int endBitPos = endIndex - (endBytePos * 8);
        assert 0 <= endBytePos && endBytePos < this.size;
        assert 0 <= endBitPos && endBitPos < 8;

        if (beginBytePos == endBytePos) {
            if (value) {
                for (int bitPos = beginBitPos; bitPos < endBitPos; ++bitPos) {
                    this.body[beginBytePos] |= (1 << bitPos);
                }
            } else {
                for (int bitPos = beginBitPos; bitPos < endBitPos; ++bitPos) {
                    this.body[beginBytePos] &= ~(1 << bitPos);
                }
            }
        } else {
            if (value) {
                for (int bitPos = beginBitPos; bitPos < 8; ++bitPos) {
                    this.body[beginBytePos] |= (1 << bitPos);
                }
                java.util.Arrays.fill(this.body, beginBytePos + 1, endBytePos, (byte) 0xff);
                for (int bitPos = 0; bitPos < endBitPos; ++bitPos) {
                    this.body[endBytePos] |= (1 << bitPos);
                }
            } else {
                for (int bitPos = beginBitPos; bitPos < 8; ++bitPos) {
                    this.body[beginBytePos] &= ~(1 << bitPos);
                }
                java.util.Arrays.fill(this.body, beginBytePos + 1, endBytePos, (byte) 0);
                for (int bitPos = 0; bitPos < endBitPos; ++bitPos) {
                    this.body[endBytePos] &= ~(1 << bitPos);
                }
            }
        }
    }

    public int count() {
        if (this.size == 0) {
            return 0;
        }

        final int byteSize = (this.size + (8 - 1)) / 8;
        final int lastByteIndex = byteSize - 1;

        int bitCount = 0;
        for (int i = 0; i < lastByteIndex; ++i) {
            int unsignedValue = (this.body[i] + 0x100) % 0x100;
            bitCount += bitCountPatterns[unsignedValue];
        }
        final int lastByteBits = this.size - 8 * lastByteIndex;
        for (int i = 0; i < lastByteBits; ++i) {
            if ((this.body[lastByteIndex] & (1 << i)) != 0) {
                bitCount += 1;
            }
        }
        return bitCount;
    }

}
