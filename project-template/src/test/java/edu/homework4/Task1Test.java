package edu.homework4;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class Task1Test {
    @Test
    public void testSortByHeight() {
        List<Animal> animals = createSampleAnimals();
        List<Animal> sortedAnimals = Task1.sortByHeight(animals);
        for (int i = 0; i < sortedAnimals.size() - 1; i++) {
            assertTrue(sortedAnimals.get(i).height() <= sortedAnimals.get(i + 1).height());
        }
    }

    @Test
    public void testSortAndTakeTopKByWeight() {
        List<Animal> animals = createSampleAnimals();
        int k = 3;
        List<Animal> topKByWeight = Task1.sortAndTakeTopKByWeight(animals, k);
        assertEquals(k, topKByWeight.size());
        for (int i = 0; i < topKByWeight.size() - 1; i++) {
            assertTrue(topKByWeight.get(i).weight() >= topKByWeight.get(i + 1).weight());
        }
    }

    @Test
    public void testCountAnimalsByType() {
        List<Animal> animals = createSampleAnimals();
        Map<Animal.Type, Integer> countByType = Task1.countAnimalsByType(animals);
        int i1 = countByType.get(Animal.Type.CAT);
        int i2 = countByType.get(Animal.Type.DOG);
        int i3 = countByType.get(Animal.Type.BIRD);
        assertEquals(2, i1);
        assertEquals(2, i2);
        assertEquals(1, i3);
    }


    private List<Animal> createSampleAnimals() {
        List<Animal> animals = new ArrayList<>();
        animals.add(new Animal("Cat1", Animal.Type.CAT, Animal.Sex.M, 3, 25, 5, false));
        animals.add(new Animal("Cat2", Animal.Type.CAT, Animal.Sex.F, 4, 23, 4, false));
        animals.add(new Animal("Dog1", Animal.Type.DOG, Animal.Sex.M, 5, 30, 8, true));
        animals.add(new Animal("Dog2", Animal.Type.DOG, Animal.Sex.F, 6, 27, 7, true));
        animals.add(new Animal("Bird", Animal.Type.BIRD, Animal.Sex.M, 2, 15, 1, false));
        return animals;
    }

}
