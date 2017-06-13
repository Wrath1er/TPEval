package fr.aifcc.master.directory_bean;

import fr.aifcc.master.directory_api.*;
import java.util.Collection;
import javax.faces.bean.*;


@ManagedBean( name = "personBean" )
@ViewScoped
public class PersonBean
{

    @ManagedProperty( value = "#{directoryBean}" )
    private DirectoryBean directoryBean;

    private long id = 0;
    private boolean pageSuivante = false;

    public PersonBean()
    {}

    public DirectoryBean getDirectoryBean()
    {
      return this.directoryBean;
    }
  
    public void setDirectoryBean( DirectoryBean directoryBean )
    {
      this.directoryBean = directoryBean;
    }
  
    public long getId()
    {
      return this.id;
    }

    public void setId( long id )
    {
      this.id = id;
    }

    public Person getPerson() throws DirectoryException
    {      
        Directory dir = this.directoryBean.getDirectory();
        Person it = dir.getPerson(id);
        return it;
    }
}
