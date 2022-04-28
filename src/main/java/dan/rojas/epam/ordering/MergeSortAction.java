package dan.rojas.epam.ordering;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.RecursiveAction;

public class MergeSortAction extends RecursiveAction {

  private final static Logger logger = LoggerFactory.getLogger(MergeSortAction.class);

  private final int[] numbersArray;

  public MergeSortAction(final int[] numbersArray) {
    this.numbersArray = numbersArray;
  }

  @Override
  protected void compute() {
    if (numbersArray.length < 2) {
      return;
    }
    int pivot = numbersArray.length / 2;

    int[] left = new int[pivot];
    System.arraycopy(numbersArray, 0, left, 0, pivot);

    int[] right = new int[numbersArray.length - pivot];
    System.arraycopy(numbersArray, pivot, right, 0, numbersArray.length - pivot);

    invokeAll(new MergeSortAction(left), new MergeSortAction(right));
    mergeArrays(left, right);
  }

  private void mergeArrays(int[] left, int[] right) {
    int i = 0, j = 0, k = 0;
    while (i < left.length && j < right.length) {
      if (left[i] < right[j]) {
        numbersArray[k++] = left[i++];
      } else {
        numbersArray[k++] = right[j++];
      }
    }
    while (i < left.length) {
      numbersArray[k++] = left[i++];
    }
    while (j < right.length) {
      numbersArray[k++] = right[j++];
    }
  }

}
