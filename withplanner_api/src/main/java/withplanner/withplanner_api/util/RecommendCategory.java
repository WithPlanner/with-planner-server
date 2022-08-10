package withplanner.withplanner_api.util;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

@Component
public class RecommendCategory {
    private static final HashMap<String, List<String>> categoryMap = new HashMap<>();

    @PostConstruct
    public void initCategory() {
        List<String> network = new ArrayList<>();
        network.add("communication");
        List<String> workout = new ArrayList<>();
        workout.add("exercise");
        workout.add("homeTraining");
        workout.add("stretching");
        workout.add("diet");
        List<String> fun = new ArrayList<>();
        fun.add("hobby");
        fun.add("writing");
        List<String> life = new ArrayList<>();
        life.add("healthHabit");
        life.add("miracleMorning");
        life.add("timeManagement");
        life.add("nightRoutine");
        life.add("houseClean");
        life.add("digitalDetox");
        List<String> future = new ArrayList<>();
        future.add("prepareEmployment");
        future.add("read");
        future.add("foreignLanguage");
        future.add("mentalCare");
        categoryMap.put("network", network);
        categoryMap.put("workout", workout);
        categoryMap.put("fun", fun);
        categoryMap.put("life", life);
        categoryMap.put("future", future);
    }

    public static String recommend(String key) {
        List<String> list = categoryMap.get(key);
        Collections.shuffle(list);
        return list.get(0);
    }
}
