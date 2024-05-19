package ayds.songinfo.moredetails.presenter;

import ayds.songinfo.moredetails.fulllogic.domain.ArtistBiography;
import ayds.songinfo.moredetails.fulllogic.presentation.ArtistBiographyDescriptionHelperImpl;
import junit.framework.TestCase.assertEquals;
import org.junit.Test;

class ArtistBiographyDescriptionHelperTest {
    private val artistBiographyDescriptionHelper = ArtistBiographyDescriptionHelperImpl();

    @Test
    fun `given a local artist biography it should return adequate HTML`() {
        val artistBiography = ArtistBiography(
            "NAFTA",
            "NAFTA es una banda particular desde todo punto de\\n" +
                    "vista y escucha. La agrupación porteña, que comparte\\n" +
                    "algunos integrantes con Militantes del Climax, conjuga\\n" +
                    "géneros como el R&B, el neo-soul y el soul clásico de\\n" +
                    "una forma dificil de encontrar en la escena argentina.\\n" +
                    "Después de tres años de sacar singles y sesiones\\n" +
                    "en vivo, sacaron un álbum acompañado de una obra\\n" +
                    "audiovisual que se complementa, integra y condice a la\\n" +
                    "perfección con las canciones. El último rasgo que los\\n" +
                    "hace particulares es la decisión de permanecer entre\\n" +
                    "las sombras: en ninguna de sus redes se encuentran los\\n" +
                    "nombres de lxs integrantes, y cuando se les pregunta\\n" +
                    "al respecto su respuesta es \"nos seduce el anonimato,\\n" +
                    "vieja\". Inobjetable.",
            "",
            true
        );

        val result = artistBiographyDescriptionHelper.getDescription(artistBiography);

        val expected =
            "<html><div width=400><font face=\"arial\">" +
                    "[*]<b>NAFTA</b> es una banda particular desde todo punto de<br>" +
                    "vista y escucha. La agrupación porteña, que comparte<br>" +
                    "algunos integrantes con Militantes del Climax, conjuga<br>" +
                    "géneros como el R&B, el neo-soul y el soul clásico de<br>" +
                    "una forma dificil de encontrar en la escena argentina.<br>" +
                    "Después de tres años de sacar singles y sesiones<br>" +
                    "en vivo, sacaron un álbum acompañado de una obra<br>" +
                    "audiovisual que se complementa, integra y condice a la<br>" +
                    "perfección con las canciones. El último rasgo que los<br>" +
                    "hace particulares es la decisión de permanecer entre<br>" +
                    "las sombras: en ninguna de sus redes se encuentran los<br>" +
                    "nombres de lxs integrantes, y cuando se les pregunta<br>" +
                    "al respecto su respuesta es \"nos seduce el anonimato,<br>" +
                    "vieja\". Inobjetable." +
                    "</font></div></html>";

        assertEquals(expected, result);
    }

    @Test
    fun `given a non-local artist biography it should return adequate HTML`() {
        val artistBiography = ArtistBiography(
            "NAFTA",
            "NAFTA es una banda particular desde todo punto de\\n" +
                    "vista y escucha. La agrupación porteña, que comparte\\n" +
                    "algunos integrantes con Militantes del Climax, conjuga\\n" +
                    "géneros como el R&B, el neo-soul y el soul clásico de\\n" +
                    "una forma dificil de encontrar en la escena argentina.\\n" +
                    "Después de tres años de sacar singles y sesiones\\n" +
                    "en vivo, sacaron un álbum acompañado de una obra\\n" +
                    "audiovisual que se complementa, integra y condice a la\\n" +
                    "perfección con las canciones. El último rasgo que los\\n" +
                    "hace particulares es la decisión de permanecer entre\\n" +
                    "las sombras: en ninguna de sus redes se encuentran los\\n" +
                    "nombres de lxs integrantes, y cuando se les pregunta\\n" +
                    "al respecto su respuesta es \"nos seduce el anonimato,\\n" +
                    "vieja\". Inobjetable.",
            "",
            true
        );

        val result = artistBiographyDescriptionHelper.getDescription(artistBiography);

        val expected =
            "<html><div width=400><font face=\"arial\">" +
                    "[*]<b>NAFTA</b> es una banda particular desde todo punto de<br>" +
                    "vista y escucha. La agrupación porteña, que comparte<br>" +
                    "algunos integrantes con Militantes del Climax, conjuga<br>" +
                    "géneros como el R&B, el neo-soul y el soul clásico de<br>" +
                    "una forma dificil de encontrar en la escena argentina.<br>" +
                    "Después de tres años de sacar singles y sesiones<br>" +
                    "en vivo, sacaron un álbum acompañado de una obra<br>" +
                    "audiovisual que se complementa, integra y condice a la<br>" +
                    "perfección con las canciones. El último rasgo que los<br>" +
                    "hace particulares es la decisión de permanecer entre<br>" +
                    "las sombras: en ninguna de sus redes se encuentran los<br>" +
                    "nombres de lxs integrantes, y cuando se les pregunta<br>" +
                    "al respecto su respuesta es \"nos seduce el anonimato,<br>" +
                    "vieja\". Inobjetable." +
                    "</font></div></html>";

        assertEquals(expected, result);
    }
}
