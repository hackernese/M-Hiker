using M_Hike.Database.Models;
using System.Diagnostics;
using CommunityToolkit.Maui.Alerts;
using CommunityToolkit.Maui.Core;
using M_Hike.Database;
using System.ComponentModel;

namespace M_Hike.Views;



public class LocationWrapper
{
    public LocationWrapper(Placemark l)
    {
        

        this.l = l;
        this.lat = l.Location.Latitude;
        this.long_ = l.Location.Longitude;

        Text = $"{l.FeatureName} {l.Thoroughfare} {l.SubAdminArea} {l.AdminArea} {l.CountryName}";

        Trace.WriteLine(Text);
    }

    public LocationWrapper() { }

    public string Text { get; set; }

    double lat = 0;
    double long_ = 0;

    Placemark l;
}
[QueryProperty("HikeItem", "item")]
public partial class AddHike : ContentPage
{
    Hike item = null;

    DatabaseHandler db;

    public Hike HikeItem
    {
        set
        {
            item = value;
            if (item != null)
            {
                titlebar.Title = "Edit Hike";


                // Extracting default location and bind to the input

                slider.Value = item.companions;
                editor.Text = item.description;
                locationame.Text = item.location;

                unitpicker.SelectedItem = item.units;
                levelpicker.SelectedItem = item.level;

                switcher.IsToggled = item.parking;

                lat.Text = item.lat.ToString();
                @long.Text = item.longtitude.ToString();

                hikename.Text = item.namne;
                lengthbox.Text = item.length.ToString();


                DateTime date;
                if(DateTime.TryParse(item.date, out date))
                {
                    datepicker.Date = date;
                }

         
            }
        }
    }
    public AddHike(DatabaseHandler db)
    {
        InitializeComponent();

        this.db = db;

        // Initializing some stuffs for the views
        List<string> strls = new List<string>{
            "Kilometers",
            "Miles", "Meters"
        }, strlslevel = new List<string>
        {
            "Hard", "Intermediate", "Easy"
        };
        unitpicker.ItemsSource = strls;
        levelpicker.ItemsSource = strlslevel;

        if(item==null)
            Device.BeginInvokeOnMainThread(async () =>
            {
                await GetCurrentLocation();
            });
    }

    private CancellationTokenSource _cancelTokenSource;
    private bool checking_location;
    private Location location = null;
    public async Task GetCurrentLocation()
    {
        try
        {
            checking_location = true;

            GeolocationRequest request = new GeolocationRequest(GeolocationAccuracy.Medium, TimeSpan.FromSeconds(10));

            _cancelTokenSource = new CancellationTokenSource();

            location = await Geolocation.Default.GetLocationAsync(request, _cancelTokenSource.Token);

            if (location != null) { 
                Trace.WriteLine($"Successfully extracted location");

                var placemarks = await Geocoding.GetPlacemarksAsync(location);
                var placemarkDetails = placemarks?.FirstOrDefault();


                @long.Text = location.Longitude.ToString();
                lat.Text = location.Latitude.ToString();

                firstloaded = true;

                locationame.Text = $"{placemarkDetails.FeatureName} {placemarkDetails.Thoroughfare} {placemarkDetails.SubAdminArea} {placemarkDetails.AdminArea} {placemarkDetails.CountryName}";

            }
        }
        // Catch one of the following exceptions:
        //   FeatureNotSupportedException
        //   FeatureNotEnabledException
        //   PermissionException
        catch (Exception ex)
        {
            // Unable to get location
            Trace.WriteLine(ex);
        }
        finally
        {
            checking_location = false;
            locationlist.IsVisible = false;
        }
    }

    private bool firstloaded = false;

    public void CancelRequest()
    {
        if (checking_location && _cancelTokenSource != null && _cancelTokenSource.IsCancellationRequested == false)
            _cancelTokenSource.Cancel();
    }

    private async void Button_Clicked(object sender, EventArgs e)
    {
        await GetCurrentLocation();
    }

    private async void SaveHike(object sender, EventArgs e)
    {

        string is_error = "";



        // Grabbing optional fields
        int companion = (int)slider.Value;
        string description = editor.Text != null ? editor.Text.ToString() : string.Empty;

        // Grabbing necessary field 
        string location = locationame.Text;
        string unit = unitpicker.SelectedItem != null ? unitpicker.SelectedItem.ToString() : "";
        string level = levelpicker.SelectedItem != null ? levelpicker.SelectedItem.ToString() : "";
        bool isparking = switcher.IsToggled;
        double latitude = lat.Text.Equals("UNKNOWN") ? 0 : Double.Parse(lat.Text);
        double longtitude = @long.Text.Equals("UNKNOWN") ? 0 : Double.Parse(@long.Text);
        string datestr = datepicker.Date.ToString();
        string name = hikename.Text;
        int length;

        if (name == null || name.Trim().Length == 0)
            is_error = "Please provide a name.";
        else if (location == null || location.Trim().Length == 0)
            is_error = "Please provide a location.";
        else if (lengthbox.Text == null || lengthbox.Text.Trim().Length == 0)
            is_error = "Please provide a length.";
        else if (unit == null || unit.Trim().Length == 0)
            is_error = "Please provide a unit.";
        else if (level == null || level.Trim().Length == 0)
            is_error = "Please provide difficulty.";


        if (is_error.Length == 0)
        {
            // No errors
            length = int.Parse(lengthbox.Text);

            // Save to database
            Hike hike = (item==null ? new Hike() : item).buildparams(name, location,latitude,longtitude, datestr, unit, level, length, isparking, companion, description);

            db.SaveItemAsync(hike);
            await Shell.Current.GoToAsync("..", true, new Dictionary<string, object>{
                ["reset"] = true
            });

        }
        else

            // Show error
            show_error(is_error);
    }

    private async void show_error(string err)
    {

        Trace.WriteLine(err);

        if (err.Trim().Length == 0)
            return;

        CancellationTokenSource cancellationTokenSource = new CancellationTokenSource();

        var snackbarOptions = new SnackbarOptions
        {
            BackgroundColor = Colors.Tomato,
            TextColor = Colors.White,
            CornerRadius = new CornerRadius(10),

        };
        string actionButtonText = "X";
        Action action = async () => { };
        TimeSpan duration = TimeSpan.FromSeconds(3);

        var snackbar = Snackbar.Make(err, action, actionButtonText, duration, snackbarOptions);

        await snackbar.Show(cancellationTokenSource.Token);
    }

    private void Slider_ValueChanged(object sender, ValueChangedEventArgs e)
    {
        int value = (int)e.NewValue;
        companionlabel.Text =  value.ToString();
    }

    private async void locationame_TextChanged(object sender, TextChangedEventArgs e)
    {
        if (e.NewTextValue == null || e.NewTextValue.Trim().Length == 0)
        {
            lat.Text = "UNKNOWN";
            @long.Text = "UNKNOWN";
            listlocation.ItemsSource = new List<LocationWrapper>();
            locationlist.IsVisible = false;
            return;
        }

        IEnumerable<Location> locations = await Geocoding.Default.GetLocationsAsync(e.NewTextValue);
        List<LocationWrapper> list_ = new List<LocationWrapper>();

        foreach (Location l in locations)
        {
            if (l != null)
            {

                var placemarks = await Geocoding.GetPlacemarksAsync(l);
                var placemarkDetails = placemarks?.FirstOrDefault();

                list_.Add(new LocationWrapper(placemarkDetails));

                Trace.WriteLine($"Latitude: {l.Latitude}, Longitude: {l.Longitude}, Altitude: {l.Altitude}");

            }
        }


        // Adding to location
        listlocation.ItemsSource = list_;

        if (firstloaded)
        {
            firstloaded = false;
            return;
        }

        if(list_.Count > 0)
        {
            locationlist.IsVisible = true;
        }
        else
        {
            locationlist.IsVisible = false;

        }

    }

    private void locationame_Focused(object sender, FocusEventArgs e)
    {
        locationlist.IsVisible = true;
    }

    private void locationame_Unfocused(object sender, FocusEventArgs e)
    {
        locationlist.IsVisible = false;
    }
}