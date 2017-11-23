package uk.gov.ukho.ildar;


import com.yahoo.elide.Elide;
import com.yahoo.elide.ElideResponse;


import com.yahoo.elide.ElideSettingsBuilder;
import com.yahoo.elide.core.DataStore;
import com.yahoo.elide.core.HttpStatus;
import com.yahoo.elide.datastores.hibernate5.HibernateStore;
import org.hibernate.SessionFactory;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.HandlerMapping;

import javax.persistence.EntityManagerFactory;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import java.util.Map;

/**
 * The rest interface
 */
@RestController
public class Dummy {
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(Dummy.class);

    @Autowired
    private EntityManagerFactory emf;

    @Autowired
    private Elide elide;

    /**
     * Converts a plain map to a multivalued map * @param input The original map * @return A MultivaluedMap constructed from the input
     */
    private MultivaluedMap<String, String> fromMap(final Map<String, String> input) {
        return new MultivaluedHashMap<String, String>(input);
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, value = {"/{entity}", "/{entity}/{id}/relationships/{entity2}", "/{entity}/{id}/{child}", "/{entity}/{id}"})
    @Transactional
    public ResponseEntity<String> jsonApiGet(@RequestParam final Map<String, String> allRequestParams, final HttpServletRequest request) {
        final String restOfTheUrl = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
        final MultivaluedMap<String, String> params = fromMap(allRequestParams);
        final String fixedPath = restOfTheUrl.replaceAll("^/", "");
        final ElideResponse response = elide.get(fixedPath, params, new Object());
        return new ResponseEntity<String>(response.getBody(), org.springframework.http.HttpStatus.valueOf(response.getResponseCode()));
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, value = {"/{entity}", "/{entity}/{id}/{child}"})
    @Transactional
    public ResponseEntity<String> jsonApiPost(@RequestBody final String body, final HttpServletRequest request) {
        final String restOfTheUrl = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
        final String fixedPath = restOfTheUrl.replaceAll("^/", "");
        /* Return the JSON response to the client */
        final ElideResponse response = elide.post(fixedPath, body, new Object());
        return new ResponseEntity<String>(response.getBody(), org.springframework.http.HttpStatus.valueOf(response.getResponseCode()));
    }


}
