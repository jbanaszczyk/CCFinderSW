package clonedetector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.TreeSet;

/**
 * A class that computes clone set information from a clone pair
 */
public class Pair2Set {
    private final ArrayList<PlaceAndRoad> placeAndRoadList = new ArrayList<>();

    private static int compareDistanceForBack(int[] s, int[] t) {
        if (s[2] < t[2])
            return -1;
        else if (s[2] > t[2])
            return 1;
        else {
            if (s[0] < t[0]) {
                return -1;
            } else if (s[0] > t[0]) {
                return 1;
            } else {
                if (s[1] < t[1]) {
                    return -1;
                } else if (s[1] > t[1]) {
                    return 1;
                }
            }
        }
        return 0;
    }

    public static int compareForBack(int[] s, int[] t) {
        if (s[0] < t[0]) {
            return -1;
        } else if (s[0] > t[0]) {
            return 1;
        } else {
            if (s[1] < t[1]) {
                return -1;
            } else if (s[1] > t[1]) {
                return 1;
            }
        }
        return 0;
    }

    public static int CloneIDFor(int[] a, int[] b) {
        if (a[3] < b[3]) {
            return -1;
        } else if (a[3] > b[3]) {
            return 1;
        } else {
            if (a[0] < b[0]) {
                return -1;
            } else if (a[0] > b[0]) {
                return 1;
            }
        }
        return 0;
    }

    /**
     * Receive a pair and return a clone pair with clone set information added
     * (Distinguish whether it is a clone set by clone ID)
     */
    int[][] makeCloneSet(int[][] pair) {
        System.out.println("Shaping Clone Pairs...");

        // Only those with the same length will be clone sets, so sort
        Arrays.sort(pair, Pair2Set::compareDistanceForBack);

        // Collect clones with the same length and determine whether they are a clone set
        int nowDistance = 0;
        int i = 0;
        int cloneID = 1;
        for (int[] aClonePairListX : pair) {
            if (nowDistance != aClonePairListX[2]) {
                cloneID = registerCloneSet(pair, cloneID);
                nowDistance = aClonePairListX[2];
            }
            makePlaceAndRoadList(aClonePairListX, i);
            i++;
        }
        registerCloneSet(pair, cloneID);
        return pair;
    }

    private void makePlaceAndRoadList(int[] pair, int index) {
        int forward = pair[0];
        int backward = pair[1];
        PlaceAndRoad aPar = parAddList(backward);
        PlaceAndRoad bPar = parAddList(forward);
        aPar.direction.add(bPar);
        bPar.direction.add(aPar);
        aPar.indexOfPair.add(index);
        bPar.indexOfPair.add(index);
    }

    private PlaceAndRoad parAddList(int a) {
        int index;
        PlaceAndRoad tmpPar;
        if ((index = parContain(a)) == -1) {
            tmpPar = new PlaceAndRoad(a);
            placeAndRoadList.add(tmpPar);
        } else {
            tmpPar = placeAndRoadList.get(index);
        }
        return tmpPar;
    }

    private int parContain(int place) {
        for (int i = 0; i < placeAndRoadList.size(); i++) {
            if (placeAndRoadList.get(i).place == place) {
                return i;
            }
        }
        return -1;
    }

    private int registerCloneSet(int[][] pair, int cloneID) {
        if (placeAndRoadList.size() == 0) {
            return cloneID;
        }

        for (PlaceAndRoad par : placeAndRoadList) {
            if (par.visited) {
                continue;
            }
            par.visited = true;
            TreeSet<Integer> cs = new TreeSet<>();
            snake(par, cs); // cs will be ready when i come back
            for (Integer x : cs) {
                pair[x][3] = cloneID;
            }
            cloneID++;
        }

        placeAndRoadList.clear();
        return cloneID;
    }

    private void snake(PlaceAndRoad par, TreeSet<Integer> cs) {
        for (PlaceAndRoad parChild : par.direction) {
            if (parChild.visited) {
                continue;
            }
            parChild.visited = true;
            cs.addAll(parChild.indexOfPair);
            snake(parChild, cs);
        }
    }

    /**
     * A class that holds the location of the code fragment and the instance of the code fragment it is paired with
     */
    static class PlaceAndRoad {
        /**
         * where the code fragment appears
         */
        int place;
        /**
         * whether the search for the code fragment is finished
         */
        boolean visited = false;
        /**
         * a list of code snippets to pair with this code snippet
         */
        ArrayList<PlaceAndRoad> direction = new ArrayList<>();
        /**
         * position of clone pair containing this code fragment (index in clone pair list)
         */
        ArrayList<Integer> indexOfPair = new ArrayList<>();

        PlaceAndRoad(int place) {
            this.place = place;
        }
    }
}
