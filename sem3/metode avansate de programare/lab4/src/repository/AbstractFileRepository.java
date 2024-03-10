package repository;

import domain.Entity;
import domain.validators.ValidationException;
import domain.validators.Validator;

import java.io.*;
import java.util.Arrays;
import java.util.List;

public abstract class AbstractFileRepository<ID,E extends Entity<ID>> extends InMemoryRepository<ID,E> {
    String fileName;
    public AbstractFileRepository(String fileName, Validator<E> validator) throws ValidationException {
        super(validator);
        this.fileName=fileName;
        loadData();
    }

    private void loadData() throws ValidationException {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String linie;
            while((linie=br.readLine())!=null){
                List<String> attr= Arrays.asList(linie.split(";"));
                E e=extractEntity(attr);
                super.save(e);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *  extract entity  - template method design pattern
     *  creates an entity of type E having a specified list of @code attributes
     * @param attributes
     * @return an entity of type E
     */
    public abstract E extractEntity(List<String> attributes);

    protected abstract String createEntityAsString(E entity);

    @Override
    public E save(E entity) throws ValidationException {
        E e=super.save(entity);
        if (e==null)
        {
            writeToFile(entity);
        }
        return e;
    }

    @Override
    public E update(E entity) throws ValidationException {
        E e = super.update(entity);
        if (e == null)
        {
            this.refreshFile(super.findAll());
        }
        return e;
    }

    @Override
    public E remove(ID id)
    {
        E e = super.remove(id);
        if (e != null)
        {
            this.refreshFile(super.findAll());
        }
        return e;
    }

    protected void writeToFile(E entity){
        try (BufferedWriter bW = new BufferedWriter(new FileWriter(fileName,true))) {
            bW.write(createEntityAsString(entity));
            bW.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void refreshFile(Iterable<E> entities)
    {
        try (BufferedWriter bW = new BufferedWriter(new FileWriter(this.fileName, false)))
        {
            for (E entity : entities)
            {
                bW.write(createEntityAsString(entity));
                bW.newLine();
            }
        }
        catch (IOException exception)
        {
            exception.printStackTrace();
        }
    }

    @Override
    public long size(){
        return super.size();
    }

}
