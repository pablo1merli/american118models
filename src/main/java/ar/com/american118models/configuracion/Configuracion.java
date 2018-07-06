package ar.com.american118models.configuracion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@PropertySource(value = "classpath:configuracion.properties", ignoreResourceNotFound = true)
public class Configuracion
{
	@Autowired
	Environment environment;

	public String getValorDeProperty(String property)
	{
		return environment.getProperty(property);
	}
}
