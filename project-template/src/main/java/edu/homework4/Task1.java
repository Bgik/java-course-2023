package edu.homework4;

import java.util.*;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;

public class Task1 {

    //1
    public static List<Animal> sortByHeight(List<Animal> animals) {
        return animals.stream()
                .sorted(Comparator.comparing(Animal::height))
                .collect(Collectors.toList());
    }

    // 2
    public static List<Animal> sortAndTakeTopKByWeight(List<Animal> animals, int k) {
        return animals.stream()
                .sorted(Comparator.comparing(Animal::weight).reversed())
                .limit(k)
                .collect(Collectors.toList());
    }

    // 3
    public static Map<Animal.Type, Integer> countAnimalsByType(List<Animal> animals) {
        return animals.stream()
                .collect(Collectors.groupingBy(Animal::type, Collectors.summingInt(a -> 1)));
    }

    // 4
    public static Animal findAnimalWithLongestName(List<Animal> animals) {
        return animals.stream()
                .max(Comparator.comparing(a -> a.name().length()))
                .orElse(null);
    }

    // 5
    public static Animal.Sex moreMalesOrFemales(List<Animal> animals) {
        long males = animals.stream().filter(a -> a.sex() == Animal.Sex.M).count();
        long females = animals.stream().filter(a -> a.sex() == Animal.Sex.F).count();
        return (males > females) ? Animal.Sex.M : Animal.Sex.F;
    }

    // 6
    public static Map<Animal.Type, Animal> findHeaviestAnimalForEachType(List<Animal> animals) {
        return animals.stream()
                .collect(Collectors.toMap(Animal::type, animal -> animal,
                        BinaryOperator.maxBy(Comparator.comparing(Animal::weight))));
    }

    // 7
    public static Animal findKthOldestAnimal(List<Animal> animals, int k) {
        return animals.stream()
                .sorted(Comparator.comparing(Animal::age).reversed())
                .skip(k - 1)
                .findFirst()
                .orElse(null);
    }

    // 8
    public static Optional<Animal> findHeaviestAnimalBelowHeight(List<Animal> animals, int height) {
        return animals.stream()
                .filter(a -> a.height() < height)
                .max(Comparator.comparing(Animal::weight));
    }

    // 9
    public static int sumPaws(List<Animal> animals) {
        return animals.stream()
                .mapToInt(Animal::paws)
                .sum();
    }

    // 10
    public static List<Animal> findAnimalsWithMismatchedAgeAndPaws(List<Animal> animals) {
        return animals.stream()
                .filter(a -> a.age() != a.paws())
                .collect(Collectors.toList());
    }

    // 11
    public static List<Animal> findBitingAnimalsWithHeightAbove100(List<Animal> animals) {
        return animals.stream()
                .filter(a -> a.bites() && a.height() > 100)
                .collect(Collectors.toList());
    }

    // 12
    public static int countAnimalsWithWeightAboveHeight(List<Animal> animals) {
        return (int) animals.stream()
                .filter(a -> a.weight() > a.height())
                .count();
    }

    // 13
    public static List<Animal> findAnimalsWithMoreThanTwoWordsInName(List<Animal> animals) {
        return animals.stream()
                .filter(a -> a.name().split(" ").length > 2)
                .collect(Collectors.toList());
    }

    // 14
    public static boolean hasDogWithHeightAboveK(List<Animal> animals, int k) {
        return animals.stream()
                .anyMatch(a -> a.type() == Animal.Type.DOG && a.height() > k);
    }

    // 15
    public static Map<Animal.Type, Integer> sumWeightByTypeInRange(List<Animal> animals, int fromAge, int toAge) {
        return animals.stream()
                .filter(a -> a.age() >= fromAge && a.age() <= toAge)
                .collect(Collectors.groupingBy(Animal::type, Collectors.summingInt(Animal::weight)));
    }

    // 16
    public static List<Animal> sortAnimalsByTypeThenSexThenName(List<Animal> animals) {
        return animals.stream()
                .sorted(Comparator.comparing(Animal::type)
                        .thenComparing(Animal::sex)
                        .thenComparing(Animal::name))
                .collect(Collectors.toList());
    }

    // 17
    public static boolean doSpidersBiteMoreThanDogs(List<Animal> animals) {
        long spiderCount = animals.stream()
                .filter(a -> a.type() == Animal.Type.SPIDER)
                .count();
        long dogCount = animals.stream()
                .filter(a -> a.type() == Animal.Type.DOG)
                .count();
        if (spiderCount == 0 || dogCount == 0) {
            return false;
        }
        return (spiderCount / (double) dogCount) > 1.0;
    }

    // 18
    public static Animal findHeaviestFishInLists(List<List<Animal>> animalLists) {
        return animalLists.stream()
                .flatMap(List::stream)
                .filter(a -> a.type() == Animal.Type.FISH)
                .max(Comparator.comparing(Animal::weight))
                .orElse(null);
    }

    // 19
    public static class ValidationError {
        private final String message;

        public ValidationError(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }

    public static Map<String, Set<ValidationError>> findAnimalsWithErrors(List<Animal> animals) {
        Map<String, Set<ValidationError>> animalsWithErrors = new HashMap<>();
        for (Animal animal : animals) {
            Set<ValidationError> errors = new HashSet<>();

            if (animal.name().split(" ").length > 2) {
                errors.add(new ValidationError("Name has more than two words"));
            }
            // ������ �������� ����� �������� �� �������������

            if (!errors.isEmpty()) {
                animalsWithErrors.put(animal.name(), errors);
            }
        }
        return animalsWithErrors;
    }

    //20
    public static Map<String, String> getReadableErrorsForAnimals(List<Animal> animals) {
        Map<String, Set<ValidationError>> animalsWithErrors = findAnimalsWithErrors(animals);
        Map<String, String> readableErrors = new HashMap<>();

        for (Map.Entry<String, Set<ValidationError>> entry : animalsWithErrors.entrySet()) {
            String animalName = entry.getKey();
            Set<ValidationError> errors = entry.getValue();


            String errorMessages = errors.stream()
                    .map(ValidationError::getMessage)
                    .collect(Collectors.joining("; "));

            readableErrors.put(animalName, errorMessages);
        }
        return readableErrors;
    }
}
