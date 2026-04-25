package com.example.yoga_app;

import java.util.*;

public class ExerciseInstructions {

    private static final Map<Integer, List<String>> map = new HashMap<>();

    static {

        map.put(1, Arrays.asList(
                "Mountain Climber.",
                "Step 1. Start in the push up position with arms straight beneath shoulders.",
                "Step 2. Keep body straight from shoulders to ankles.",
                "Step 3. Bring one knee towards chest keeping body straight.",
                "Step 4. Return and repeat with opposite leg."
        ));

        map.put(2, Arrays.asList(
                "Basic Crunches.",
                "Step 1. Lie flat with knees bent and feet on floor.",
                "Step 2. Hands behind head, elbows out.",
                "Step 3. Lift head and shoulders using abdominals.",
                "Step 4. Lower slowly with control."
        ));

        map.put(3, Arrays.asList(
                "Bench Dips.",
                "Step 1. Place bench behind and rest hands on it.",
                "Step 2. Extend legs forward.",
                "Step 3. Bend elbows keeping body straight.",
                "Step 4. Lower until arms form ninety degrees.",
                "Step 5. Push back up slowly."
        ));

        map.put(4, Arrays.asList(
                "Bicycle Crunches.",
                "Step 1. Lie flat with lower back pressed to ground.",
                "Step 2. Hands behind head and knees to chest.",
                "Step 3. Touch opposite elbow to knee.",
                "Step 4. Switch sides and repeat."
        ));

        map.put(5, Arrays.asList(
                "Leg Raise.",
                "Step 1. Lie flat with hands at sides.",
                "Step 2. Raise legs off ground keeping abs tight.",
                "Step 3. Lift legs to ninety degrees.",
                "Step 4. Lower slowly without touching floor."
        ));

        map.put(6, Arrays.asList(
                "Alternative Heel Touch.",
                "Step 1. Lie on back with knees bent.",
                "Step 2. Touch right heel with right hand.",
                "Step 3. Return and touch left heel."
        ));

        map.put(7, Arrays.asList(
                "Leg Up Crunches.",
                "Step 1. Lie face up with legs straight.",
                "Step 2. Raise legs perpendicular to floor.",
                "Step 3. Perform crunch motion."
        ));

        map.put(8, Arrays.asList(
                "Sit Up.",
                "Step 1. Lie down with knees bent.",
                "Step 2. Hands behind head.",
                "Step 3. Lift upper body forming V shape.",
                "Step 4. Lower slowly."
        ));

        map.put(9, Arrays.asList(
                "Alternative V Ups.",
                "Step 1. Lie on back and lift straight leg.",
                "Step 2. Touch opposite hand to toe.",
                "Step 3. Alternate sides."
        ));

        map.put(10, Arrays.asList(
                "Plank Rotation.",
                "Step 1. Start in high plank position.",
                "Step 2. Rotate torso raising arm.",
                "Step 3. Return to plank."
        ));

        map.put(11, Arrays.asList(
                "Plank With Leg Lift.",
                "Step 1. Elbows under shoulders.",
                "Step 2. Keep body straight.",
                "Step 3. Lift one leg and hold."
        ));

        map.put(12, Arrays.asList(
                "Russian Twist.",
                "Step 1. Sit forming V shape.",
                "Step 2. Extend arms forward.",
                "Step 3. Twist torso right.",
                "Step 4. Twist torso left."
        ));

        map.put(13, Arrays.asList(
                "Bridge.",
                "Step 1. Lie on back with knees bent.",
                "Step 2. Lift pelvis upward.",
                "Step 3. Squeeze glutes.",
                "Step 4. Lower slightly and repeat."
        ));

        map.put(14, Arrays.asList(
                "Vertical Leg Crunches.",
                "Step 1. Lie face down.",
                "Step 2. Raise arms, legs and chest.",
                "Step 3. Hold briefly.",
                "Step 4. Lower slowly."
        ));

        map.put(15, Arrays.asList(
                "Wind Mill.",
                "Step 1. Stand with arms extended.",
                "Step 2. Bend forward at waist.",
                "Step 3. Touch opposite foot.",
                "Step 4. Rotate to other side."
        ));
    }

    public static List<String> get(int id) {
        return map.get(id);
    }
}