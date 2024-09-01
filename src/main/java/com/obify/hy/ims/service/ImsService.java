package com.obify.hy.ims.service;

import java.util.List;

public interface ImsService<T,R>  {
    public T add(R input);
    public T update(R input, String id);
    public String delete(String id);
    public T get(String id);
    public List<T> getAll();
    public List<T> search(R input);
}
