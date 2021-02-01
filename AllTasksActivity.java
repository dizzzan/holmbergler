@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_tasks);


        LoadDAta();



    }

    private void LoadDAta(){

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        Map<String, ?> allSharedPreferences = preferences.getAll();
        arrayList = new ArrayList<ToDo>();

        for (Map.Entry<String, ?> entry: allSharedPreferences.entrySet()){
            ToDo toDo = new ToDo();
            toDo.id = Integer.parseInt(entry.getKey());
            toDo.name = entry.getValue().toString();

            arrayList.add(toDo);
            lastKeyID = toDo.id;
        }

        adapter = new ToDoListAdapter(this, (ArrayList<ToDo>) arrayList);

        ListView listView = (ListView) findViewById(R.id.lstView);
        listView.setAdapter(adapter);
        listView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                //removeItemFromList(position);
                return true;
            }
        });




    }



}
