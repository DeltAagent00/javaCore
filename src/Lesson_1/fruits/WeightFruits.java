package Lesson_1.fruits;

public enum WeightFruits {
    Apple(1f),
    Orange(1.5f);

    private float weight;

    WeightFruits(float weight) {
        this.weight = weight;
    }

    public float getWeight() {
        return weight;
    }
}
