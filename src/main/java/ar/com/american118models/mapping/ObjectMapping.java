package ar.com.american118models.mapping;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ObjectMapping
{
	@Autowired
	private Mapper dozerMapper;
	
	@SuppressWarnings("all")
	public Object convertir(Object origen,  Class destino)
	{
		return dozerMapper.map(origen, destino);
	}
}
