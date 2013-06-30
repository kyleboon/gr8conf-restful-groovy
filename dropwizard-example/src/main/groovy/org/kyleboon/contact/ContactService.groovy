package org.kyleboon.contact

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
        new HibernateBundle<ContactConfiguration>([]) {
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
            name = 'Contact'
            addBundle migrationsBundle
            addBundle hibernateBundle
        }
    }

    @Override
    public void run(ContactConfiguration configuration,
                    Environment environment) throws ClassNotFoundException {
    }
}
