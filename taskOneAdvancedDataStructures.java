import java.util.ArrayList;
import java.util.List;

public class ModifiedHeap {
    private List<Integer> heap;

    public ModifiedHeap() {
        heap = new ArrayList<>();
    }

    private int getChildrenCount(int index) {
        int level = (int) Math.floor(Math.log(index + 1) / Math.log(2));
        return (int) Math.pow(2, level);
    }

    private Integer getParentIndex(int index) {
        if (index == 0) return null;  // Root has no parent
        int parentLevel = (int) Math.floor(Math.log(index + 1) / Math.log(2)) - 1;
        int startOfParentLevel = (int) Math.pow(2, parentLevel) - 1;
        int childrenCount = getChildrenCount(startOfParentLevel);
        return startOfParentLevel + (index - startOfParentLevel) / childrenCount;
    }

    public void insert(int value) {
        heap.add(value);
        heapifyUp(heap.size() - 1);
    }

    private void heapifyUp(int index) {
        Integer parentIndex = getParentIndex(index);
        while (parentIndex != null && heap.get(index) > heap.get(parentIndex)) {
            swap(index, parentIndex);
            index = parentIndex;
            parentIndex = getParentIndex(index);
        }
    }

    public int popMax() {
        if (heap.isEmpty()) throw new IndexOutOfBoundsException("Heap is empty");

        int maxValue = heap.get(0);
        heap.set(0, heap.get(heap.size() - 1));
        heap.remove(heap.size() - 1);
        heapifyDown(0);
        return maxValue;
    }

    private void heapifyDown(int index) {
        while (true) {
            int childrenCount = getChildrenCount(index);
            int startChildIndex = index * childrenCount + 1;
            int endChildIndex = Math.min(startChildIndex + childrenCount, heap.size());

            if (startChildIndex >= heap.size()) break;

            int maxChildIndex = startChildIndex;
            for (int i = startChildIndex + 1; i < endChildIndex; i++) {
                if (heap.get(i) > heap.get(maxChildIndex)) {
                    maxChildIndex = i;
                }
            }

            if (heap.get(index) >= heap.get(maxChildIndex)) break;

            swap(index, maxChildIndex);
            index = maxChildIndex;
        }
    }

    private void swap(int i, int j) {
        int temp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, temp);
    }

    public static void main(String[] args) {
        ModifiedHeap heap = new ModifiedHeap();
        heap.insert(10);
        heap.insert(20);
        heap.insert(5);
        heap.insert(30);
        heap.insert(15);

        System.out.println("Max element removed: " + heap.popMax());
        System.out.println("Max element removed: " + heap.popMax());
    }
}
