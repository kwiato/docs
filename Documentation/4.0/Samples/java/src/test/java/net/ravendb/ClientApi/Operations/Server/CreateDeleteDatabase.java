package net.ravendb.ClientApi.Operations.Server;

import net.ravendb.client.documents.DocumentStore;
import net.ravendb.client.documents.IDocumentStore;
import net.ravendb.client.serverwide.DatabaseRecord;
import net.ravendb.client.serverwide.operations.CreateDatabaseOperation;
import net.ravendb.client.serverwide.operations.DeleteDatabasesOperation;

import java.time.Duration;

public class CreateDeleteDatabase {

    private interface Foo {
        /*
        //region delete_database_syntax
        public DeleteDatabasesOperation(String databaseName, boolean hardDelete)

        public DeleteDatabasesOperation(String databaseName, boolean hardDelete, String fromNode)

        public DeleteDatabasesOperation(String databaseName, boolean hardDelete, String fromNode, Duration timeToWaitForConfirmation)

        public DeleteDatabasesOperation(Parameters parameters)
        //endregion
        */

        /*
        //region create_database_syntax
        public CreateDatabaseOperation(DatabaseRecord databaseRecord)

        public CreateDatabaseOperation(DatabaseRecord databaseRecord, int replicationFactor)
        //endregion
        */

    }

    //region delete_parameters
    public static class Parameters {
        private String[] databaseNames;
        private boolean hardDelete;
        private String[] fromNodes;
        private Duration timeToWaitForConfirmation;

        public String[] getDatabaseNames() {
            return databaseNames;
        }

        public void setDatabaseNames(String[] databaseNames) {
            this.databaseNames = databaseNames;
        }

        public boolean isHardDelete() {
            return hardDelete;
        }

        public void setHardDelete(boolean hardDelete) {
            this.hardDelete = hardDelete;
        }

        public String[] getFromNodes() {
            return fromNodes;
        }

        public void setFromNodes(String[] fromNodes) {
            this.fromNodes = fromNodes;
        }

        public Duration getTimeToWaitForConfirmation() {
            return timeToWaitForConfirmation;
        }

        public void setTimeToWaitForConfirmation(Duration timeToWaitForConfirmation) {
            this.timeToWaitForConfirmation = timeToWaitForConfirmation;
        }
    }
    //endregion

    public CreateDeleteDatabase() {
        try (IDocumentStore store = new DocumentStore()) {
            //region CreateDatabase
            DatabaseRecord databaseRecord = new DatabaseRecord();
            databaseRecord.setDatabaseName("MyNewDatabase");
            store.maintenance().server().send(new CreateDatabaseOperation(databaseRecord));
            //endregion

            //region CreateEncryptedDatabase
            DatabaseRecord encryptedDatabaseRecord = new DatabaseRecord();
            encryptedDatabaseRecord.setDatabaseName("MyEncryptedDatabase");
            store.maintenance().server().send(new CreateDatabaseOperation(encryptedDatabaseRecord));
            //endregion

            //region DeleteDatabase
            store.maintenance().server().send(
                new DeleteDatabasesOperation("MyNewDatabase", true, null, null));
            //endregion

            //region DeleteDatabases
            DeleteDatabasesOperation.Parameters parameters = new DeleteDatabasesOperation.Parameters();
            parameters.setDatabaseNames(new String[]{ "MyNewDatabase", "OtherDatabaseToDelete" });
            parameters.setHardDelete(true);
            parameters.setFromNodes(new String[]{ "A", "C" }); //optional
            parameters.setTimeToWaitForConfirmation(Duration.ofSeconds(30)); // optional

            store.maintenance()
                .server().send(new DeleteDatabasesOperation(parameters));
            //endregion
        }
    }
}
