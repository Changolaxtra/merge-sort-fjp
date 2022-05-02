package dan.rojas.epam;

import dan.rojas.epam.ordering.MergeSortAction;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.stream.IntStream;

@Slf4j
public class MergeSort {

  private static final int PARALLELISM_LEVEL = 8;

  public static void main(String[] args) throws ExecutionException, InterruptedException {
    final ForkJoinPool forkJoinPool = new ForkJoinPool(PARALLELISM_LEVEL);
    int[] array = generateArray(1500);
    final long start = System.currentTimeMillis();
    log.info("Generated: " + Arrays.toString(array));
    final ForkJoinTask<Void> submit = forkJoinPool.submit(new MergeSortAction(array));
    submit.get();
    final long end = System.currentTimeMillis();
    log.info("Ordered: " + Arrays.toString(array));
    log.info("Time " + (end - start) + "ms");
  }

  private static int[] generateArray(final int size){
    final int[] array = new int[size];
    final Random random = new Random();
    IntStream.range(0, size).forEach(index -> array[index] = random.nextInt(size));
    return array;
  }

}
