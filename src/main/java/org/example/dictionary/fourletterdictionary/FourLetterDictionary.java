package org.example.dictionary.fourletterdictionary;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.translation.Translation;

import java.util.List;
import java.util.UUID;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "four_letter_dictionary")
public class FourLetterDictionary {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "four_letter_dictionary_id")
    private UUID id;

    @Column(name = "word", nullable = false, unique = true)
    private String word;

    @OneToMany(mappedBy = "fourLetterDictionary", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Translation> translations;
}