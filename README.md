# Mail-Inbox

Mail Inbox Prototype is showing data from dummy json file.

features:

1. Search by name
2. Sort by Last Active , Name, points
3. pull to refresh
4. Multiple Select and delete option

App is implemented using MVP architecture.
Dagger2 is used for dependency injection.

The app has following packages:

data: It contains all the data accessing and manipulating components.

di: Dependency providing classes using Dagger2.

user: View classes along with their corresponding Presenters.

helper: Helper classes for animaiton.

sort: classes for sorting

Presenter has bussiness logic i.e . takes the data from UserRepository and pass to the View. View has only display logic.

<h3>Screenshots</h3>
<p>
  <img src="https://github.com/vinodkamble444/Mail-Inbox/blob/master/screenshots/List_UI.png" width="300" height="550"/>
  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
  <img src="https://github.com/vinodkamble444/Mail-Inbox/blob/master/screenshots/Long_press_multi_select_UI.png" width="300" height="550"/>
</p>
<p>
  <img src="https://github.com/vinodkamble444/Mail-Inbox/blob/master/screenshots/Sort_by_UI.png" width="300" height="550"/>
   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
  <img src="https://github.com/vinodkamble444/Mail-Inbox/blob/master/screenshots/search_UI.png" width="300" height="550"/>
</p>
            
