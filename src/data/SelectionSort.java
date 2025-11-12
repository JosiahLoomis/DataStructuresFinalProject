package data;

import java.util.ArrayList;

public class SelectionSort {

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
