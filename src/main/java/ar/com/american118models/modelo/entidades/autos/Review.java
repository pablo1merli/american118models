package ar.com.american118models.modelo.entidades.autos;

import org.joda.time.LocalDate;

public class Review
{
	private String idioma;
	private String review;
	private LocalDate fecha;

	public String getIdioma()
	{
		return idioma;
	}

	public void setIdioma(String idioma)
	{
		this.idioma = idioma;
	}

	public String getReview()
	{
		return review;
	}

	public void setReview(String review)
	{
		this.review = review;
	}

	public LocalDate getFecha()
	{
		return fecha;
	}

	public void setFecha(LocalDate fecha)
	{
		this.fecha = fecha;
	}
}
