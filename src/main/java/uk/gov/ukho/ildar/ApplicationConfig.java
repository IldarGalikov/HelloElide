package uk.gov.ukho.ildar;

import com.yahoo.elide.Elide;
import com.yahoo.elide.ElideSettingsBuilder;
import com.yahoo.elide.core.DataStore;
import com.yahoo.elide.datastores.hibernate5.HibernateStore;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;

@Configuration
public class ApplicationConfig {

    @Bean
    public Elide elideBean(final EntityManagerFactory emf) {
        final SessionFactory sessionFactory = emf.unwrap(SessionFactory.class);
        final DataStore dataStore = new HibernateStore.Builder(sessionFactory).build();
        final Elide elide = new Elide(new ElideSettingsBuilder(dataStore).withUseFilterExpressions(true).build());

        return elide;
    }


}
