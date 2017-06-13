package fr.aifcc.master.directory_bean;

import fr.aifcc.master.directory_api.*;
import java.util.Collection;
import javax.faces.bean.*;


@ManagedBean( name = "personsBean" )
@ViewScoped
public class PersonsBean
{

    @ManagedProperty( value = "#{directoryBean}" )
    private DirectoryBean directoryBean;

    private long limite = 3;
    private long position = 1;

    private boolean pageSuivante = false;

    public PersonsBean()
    {}

    public DirectoryBean getDirectoryBean()
    {
      return this.directoryBean;
    }
  
    public void setDirectoryBean( DirectoryBean directoryBean )
    {
      this.directoryBean = directoryBean;
    }
  
    public void goToNextPage()
    {
      this.position = this.position + this.limite;
    }
  
    public void goToPreviousPage()
    {
      this.position = this.position - this.limite;
      if (this.position <= 0)
      {
        this.position = 1;
      }
    }
  
    public boolean hasPreviousPage()
    {
      return this.position > 1;
    }
  
  
    public boolean hasNextPage()
    {
      return this.pageSuivante;
    }

    //@RequestMapping( "/persons" )
    public Collection<Person> getPersons() throws DirectoryException
    {      
        Directory dir = this.directoryBean.getDirectory();
        Collection<Person> it = dir.getPersons(this.position, this.limite);
        this.pageSuivante = it.size() == this.limite;
        return it;
    }
}
