package org.example.translation;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class TranslationRequest extends Translation {
    private UUID fiveDigitDictionaryId;
    private UUID fourLetterDictionaryId;
}