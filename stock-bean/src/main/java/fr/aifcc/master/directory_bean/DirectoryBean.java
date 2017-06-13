package fr.aifcc.master.directory_bean;

import fr.aifcc.master.directory_api.*;
import fr.aifcc.master.directory_impl.*;

import javax.faces.bean.*;

@ManagedBean( name = "directoryBean" )
@ApplicationScoped
public class DirectoryBean
{

    private final Directory directory;

    public DirectoryBean()
        throws Exception
    {
        this.directory = new DatabaseDirectory( "org.apache.derby.jdbc.ClientDriver", "jdbc:derby://localhost:1527/nomBDD" );
    }

    //public DirectoryBean( Directory dir )
    //{
    //    this.directory = dir;
    //}

    public Directory getDirectory()
    {
        return this.directory;
    }

}