package net.pikzen.formulas;

public class Item
{
	private String Name, Id, Description;
	private String Image;
	private Boolean IsConstant = false;
	private String Category;
	
	public Item()
	{
	}
	
	public void SetName(String n)
	{
		Name = n;
	}
	public String getName()
	{
		return Name;
	}
	
	public void SetId(String id)
	{
		Id = id;
	}
	
	public void SetDescription(String desc)
	{
		Description = desc;
	}
	
	public String getDesc()
	{
		return Description;
	}
	
	public void SetImage(String image)
	{
		Image = image;
	}
	
	public String getImage()
	{
		return Image;
	}
	
	public void SetConstant(Boolean c)
	{
		IsConstant = c;
	}
	
	public void SetCategory(String c)
	{
		Category = c;
	}
	
	public String getCateg()
	{
		return Category;
	}
	
}

