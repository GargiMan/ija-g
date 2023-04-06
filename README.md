# Zadání projektu

## Základní informace

- Zadání projektu je v souboru <a href="https://github.com/GargiMan/ija-g/blob/master/IJA2022-23_Projekt.pdf">IJA2022-23_Projekt.pdf</a>.
- Zadání definuje podstatné vlastnosti aplikace, které musí být splněny. Předpokládá se, že detaily řešení si doplní řešitelské týmy.
- Nejasnosti v zadání řešte, prosím, primárně na k tomu příslušném Fóru, případně se obraťte na dr. Malinku.

## Podmínky vypracování a odevzdání projektu

- Projekt je týmový.
- Pro realizaci projektu použijte Java SE 17.
- Pro grafické uživatelské rozhraní použijte JavaFX (lze i Swing).
- Projekt bude tvořen následující základní adresářovou strukturou, která bude umístěna v kořenovém adresáři, jehož název odpovídá loginu vedoucího týmu. Tuto strukturu lze libovolně rozšířit.
  | Jméno | Typ | Popis |
  | ---- | ---- | ----------- |
  | `src/` | DIR | zdrojové soubory (hierarchie balíků) |
  | `data/` | DIR | připravené datové soubory (pokud je zadání vyžaduje) |
  | `lib/` | DIR | externí soubory a knihovny (balíky třetích stran, obrázky apod.), které vaše aplikace využívá |
  | `readme.txt` | FILE | základní popis projektu (název, členové týmu, ...), informace ke způsobu překladu a spuštění aplikace |
  | `rozdeleni.txt` | FILE | soubor obsahuje rozdělení bodů mezi členy týmu; pokud tento soubor neexistuje, předpokládá se rovnoměrné rozdělení |
  | `[pom\|build].xml` | FILE | konfigurační soubor pro překlad dle zvoleného nástroje `[maven\|ant]` |
  | `requirements.pdf` | FILE | aktualizovaný seznam požadavků |
- Všechny zdrojové soubory musí obsahovat na začátku dokumentační komentář se jmény autorů a popisem obsahu.
- V souboru `readme.txt` uveďte základní informace k projektu (autoři, způsob překladu apod.)
- Pokud vaše řešení vyžaduje další externí soubory (obrázky, jiné balíky apod.), umístěte je do adresáře lib.
- Pro překlad a spuštění využijte nástroj `ant` nebo `maven`. V souboru `readme.txt` specifikujte konkrétní způsob překladu a spuštění.
- Soubor `requirements.pdf`
  - upřesněná specifikace požadavků vytvořená v rámci 2. úkolu
  - vyhodnoťte, jak se podařilo splnit plán realizace a požadavky na systém (co bylo splněno, co nebylo splněno a proč)
- Přeložení aplikace:
  - zkompilují se zdrojové texty
  - vygeneruje se programová dokumentace
  - vytvoří se spustitelný jar archiv
- Odevzdání
  - Do adresáře data připravte alespoň dva různé mapové podklady, které budou použity při prezentaci projektu.
  - Kořenový adresář zabalte do archivu zip:
    - Název archivu bude stejný jako název kořenového adresáře (s příponou .zip), tj. login vedoucího týmu (např. `xloginXX.zip`).
    - Po rozbalení archivu vznikne adresářová struktura definovaná výše včetně adresáře `xloginXX`.
  - Archiv zip odevzdejte do IS VUT.

## Hodnocení projektu

- Projekt je hodnocen jako celek, každý člen týmu získává stejný počet bodů. Hodnocení každého člena týmu lze přerozdělit:
  - přerozdělení bodů je možné v rozsahu -50% až +50%
  - při přerozdělení může být vzata v úvahu aktivita jednotlivých členů
  - přerozdělení bodů musí být zapsáno v souboru `rozdeleni.txt` v následujícím formátu (uvádí se procenta, která získává člen týmu; součet musí odpovídat hodnotě pocet_clenu \* 100):
    ```
    xloginXX: 50
    xloginYY: 150
    ```
  - pokud nebudete body přerozdělovat, nemusíte tento soubor uvádět
  - pokud máte dojem, že některý člen týmu nepracuje a nezaslouží si ani 50% z hodnocení, je nutno toto řešit individuálně s dr. Malinkou před odevzdáním projektu
