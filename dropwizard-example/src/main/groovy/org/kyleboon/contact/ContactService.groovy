package org.kyleboon.contact

import org.kyleboon.contact.core.*
import org.kyleboon.contact.db.*
import org.kyleboon.contact.resources.*

import com.yammer.dropwizard.Service
import com.yammer.dropwizard.assets.AssetsBundle
import com.yammer.dropwizard.config.Bootstrap
import com.yammer.dropwizard.config.Environment
import com.yammer.dropwizard.db.DatabaseConfiguration
import com.yammer.dropwizard.hibernate.HibernateBundle
import com.yammer.dropwizard.migrations.MigrationsBundle

class ContactService extends Service<ContactConfiguration> {
    public static void main(String[] args) throws Exception {
        new ContactService().run(args)
    }

    HibernateBundle<ContactConfiguration> hibernateBundle =
        new HibernateBundle<ContactConfiguration>([Contact, Address]) {
            @Override
            public DatabaseConfiguration getDatabaseConfiguration(ContactConfiguration configuration) {
                return configuration.databaseConfiguration
            }
        }

    MigrationsBundle<ContactConfiguration> migrationsBundle =
        new MigrationsBundle<ContactConfiguration>() {
            @Override
            public DatabaseConfiguration getDatabaseConfiguration(ContactConfiguration configuration) {
                return configuration.databaseConfiguration
            }
        }

    AssetsBundle assetsBundle = new AssetsBundle()

    @Override
    public void initialize(Bootstrap<ContactConfiguration> bootstrap) {
        bootstrap.with {
            name = 'Contact Service'
            addBundle migrationsBundle
            addBundle hibernateBundle
            addBundle(new AssetsBundle('/apidocs/'))
            addBundle(new AssetsBundle('/swagger'))
        }
    }

    @Override
    public void run(ContactConfiguration configuration,
                    Environment environment) throws ClassNotFoundException {

        ContactDAO contactDAO = new ContactDAO(hibernateBundle.sessionFactory)
        environment.addResource(new ContactResource(contactDAO))
    }
}