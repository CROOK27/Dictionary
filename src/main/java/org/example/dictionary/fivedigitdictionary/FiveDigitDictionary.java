package org.example.dictionary.fivedigitdictionary;

import jakarta.persistence.*;
import lombok.*;
import org.example.translation.Translation;

import java.util.List;
import java.util.UUID;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "five_digit_dictionary")
public class FiveDigitDictionary {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "five_digit_dictionary_id")
    private UUID id;

    @Column(name = "word", nullable = false, unique = true)
    private String word;

    @OneToMany(mappedBy = "fiveDigitDictionary", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Translation> translations;
}