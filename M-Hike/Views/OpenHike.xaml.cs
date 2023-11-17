using M_Hike.Database.Models;

namespace M_Hike.Views;



[QueryProperty("Hike", "Hike")]
public partial class OpenHike : ContentPage
{

	Hike item = null;

	public Hike Hike
	{
		set
		{
			item = value;
		}
	}

	public OpenHike()
	{
		InitializeComponent();
	}

    private async void Delete(object sender, EventArgs e)
    {
//        await Shell.Current.GoToAsync(nameof(AddHike), true);
    }

    private async void Edit(object sender, EventArgs e)
    {
        await Shell.Current.GoToAsync(nameof(AddHike), true, new Dictionary<string, object>{
			["item"] = item
		});
    }
}