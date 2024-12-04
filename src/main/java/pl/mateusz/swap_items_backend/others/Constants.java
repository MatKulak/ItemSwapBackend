package pl.mateusz.swap_items_backend.others;

import java.util.Set;

public class Constants {

    public static final Set<String> allowedImageExtensions = Set.of(".jpg", ".jpeg", ".png",
                                                                    ".gif", ".bmp", ".tiff",
                                                                    ".webp", ".svg", ".ico");

    public static final String AI_API_QUERY =
            "Here are my advertisements in JSON format: %s. " +
            "Here are all other advertisements in JSON format: %s. " +
            "Find the best offers that might be interesting to me based on what I have and what I want to get. " +
            "If no potential matches are found, return an empty string. " +
            "Otherwise, sort the found advertisements by match probability in descending order. " +
            "Return ONLY a comma-separated list of IDs of the potential matches, with no explanations or additional text. " +
            "Output should be strictly in the following format: 'id1,id2,id3'.";

}
