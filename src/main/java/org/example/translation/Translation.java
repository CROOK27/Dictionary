package org.example.translation;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.dictionary.fivedigitdictionary.FiveDigitDictionary;
import org.example.dictionary.fourletterdictionary.FourLetterDictionary;

import java.util.UUID;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "translation")
public class Translation {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "translated_word", nullable = false)
    private String translatedWord;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "five_digit_dictionary_id")
    private FiveDigitDictionary fiveDigitDictionary;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "four_letter_dictionary_id")
    private FourLetterDictionary fourLetterDictionary;
}