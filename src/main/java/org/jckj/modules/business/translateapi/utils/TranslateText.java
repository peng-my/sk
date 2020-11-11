package org.jckj.modules.business.translateapi.utils;

import com.google.cloud.translate.v3.*;

import java.io.IOException;

public class TranslateText {
    public static void main(String[] args) throws IOException {
        translateText();
    }
    public static void translateText() throws IOException {
        // TODO(developer): Replace these variables before running the sample.
        String projectId = "neural-store-240505";
        // Supported Languages: https://cloud.google.com/translate/docs/languages
        //目标语言
        String targetLanguage = "en";
        String text = "你好";
        translateText(projectId, targetLanguage, text);
    }

    // Translating Text
    public static void translateText(String projectId, String targetLanguage, String text)
            {
        // Initialize client that will be used to send requests. This client only needs to be created
        // once, and can be reused for multiple requests. After completing all of your requests, call
        // the "close" method on the client to safely clean up any remaining background resources.
        try (TranslationServiceClient client = TranslationServiceClient.create()) {
            // Supported Locations: `global`, [glossary location], or [model location]
            // Glossaries must be hosted in `us-central1`
            // Custom Models must use the same location as your model. (us-central1)
            LocationName parent = LocationName.of(projectId, "global");

            // Supported Mime Types: https://cloud.google.com/translate/docs/supported-formats
            TranslateTextRequest request =
                    TranslateTextRequest.newBuilder()
                            .setParent(parent.toString())
                            .setMimeType("text/plain")
                            .setTargetLanguageCode(targetLanguage)
                            .addContents(text)
                            .build();

            TranslateTextResponse response = client.translateText(request);

            // Display the translation for each input text provided
            for (Translation translation : response.getTranslationsList()) {
                System.out.printf("Translated text: %s\n", translation.getTranslatedText());
            }
        }catch (Exception e) {
            System.out.println(e.toString());
        }
    }
}
// [END translate_v3_translate_text]

