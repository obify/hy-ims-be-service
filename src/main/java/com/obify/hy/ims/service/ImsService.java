package com.obify.hy.ims.service;

import java.util.List;

public interface ImsService<T,R>  {
    public T add(R input);
    public T update(R input, Long id);
    public T delete(Long id);
    public T get(Long id);
    public List<T> getAll();
    public List<T> search(R input);
}
