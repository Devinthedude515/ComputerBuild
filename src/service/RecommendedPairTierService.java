package service;

import java.util.EnumMap;
import java.util.List;

public class RecommendedPairTierService {
    
    //categorize eligible CPU/GPU combinations
    public EnumMap<RecommendationTier, RecommendedPair> calculateTiers(List<RecommendedPair> pairs) {

        //create a map to store the tier recommendations
        EnumMap<RecommendationTier, RecommendedPair> results = new EnumMap<>(RecommendationTier.class);

        //return an empty map if no pairs qualify
        if (pairs == null || pairs.isEmpty()) {
            return results;
        }

        RecommendedPair cheapest = null;
        RecommendedPair strongest = null;

        //find the lowest-cost and strongest combinations
        for (RecommendedPair pair : pairs) {

            //find lowest cost
            if (cheapest == null ||
                pair.getCombinedPrice() < cheapest.getCombinedPrice()) {

                cheapest = pair;
            }

            //find highest performance
            if (strongest == null ||
                pair.getGamingScore() > strongest.getGamingScore() ||
                (pair.getGamingScore() == strongest.getGamingScore() &&
                 pair.getCombinedPrice() < strongest.getCombinedPrice())) {

                strongest = pair;
            }
        }

        //store the cheapest combination
        results.put(RecommendationTier.LOWEST_COST, cheapest);

        //store the strongest if different
        if (strongest != cheapest) {
            results.put(RecommendationTier.HIGHEST_PERFORMANCE, strongest);
        }

        //calculate the midpoint between the two scores
        double targetScore = (cheapest.getGamingScore() + strongest.getGamingScore()) / 2.0;

        RecommendedPair balanced = null;

        //find the balanced combination
        for (RecommendedPair pair : pairs) {

            //don't select the same pair twice
            if (pair == cheapest || pair == strongest) {
                continue;
            }

            if (balanced == null) {
                balanced = pair;
                continue;
            }

            double distance =
                Math.abs(pair.getGamingScore() - targetScore);

            double bestDistance =
                Math.abs(balanced.getGamingScore() - targetScore);

            if (distance < bestDistance || (distance == bestDistance &&
                pair.getCombinedPrice() < balanced.getCombinedPrice())) {

                balanced = pair;
            }
        }

        //store the balanced combination if available
        if (balanced != null) {
            results.put(RecommendationTier.BALANCED, balanced);
        }

        return results;
    }
}
