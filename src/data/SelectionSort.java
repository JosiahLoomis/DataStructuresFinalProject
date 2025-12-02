package data;

import java.util.ArrayList;

/**
 * Class for sorting the Music List using a custom selection Sort
 * 
 * @author Josiah Loomis
 * @version 1.0
 */
public class SelectionSort {

	/**
	 * Sorts the given array list alphabetically by song title using selection sort algorithm.
	 * Modifies the list in place.
	 *
	 * @param arr the ArrayList of songs to sort
	 */
	public static void selectionSort(ArrayList<Song> arr) {
        int n = arr.size();

        for (int i = 0; i < n - 1; i++) {
            
            int minIndex = i;
            for (int j = i + 1; j < n; j++) {
                if (arr.get(j).getTitle().compareToIgnoreCase(arr.get(minIndex).getTitle()) < 0) {
                    minIndex = j;
                }
            }

            Song temp = arr.get(minIndex);
            arr.set(minIndex, arr.get(i));
            arr.set(i, temp);
        }
    }
}
