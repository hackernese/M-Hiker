using M_Hike.Views;

namespace M_Hike
{
    public partial class AppShell : Shell
    {
        public AppShell()
        {
            InitializeComponent();

            // Defining routes
            Routing.RegisterRoute(nameof(IntroSlider), typeof(IntroSlider));
            Routing.RegisterRoute(nameof(Dashboard), typeof(Dashboard));
            Routing.RegisterRoute(nameof(OpenHike), typeof(OpenHike));
            Routing.RegisterRoute(nameof(AddHike), typeof(AddHike));
        }
    }
}