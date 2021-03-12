Vue.createApp({
  data() {
    return {
      valueInput: '',
      tasks: [],
      completedTasks: [],
    };
  },

  // initial initialization of lists
  beforeCreate: function () {
    axios
      .get("/api/tasks/byStatus/?status=false")
      .then(response => {
        this.tasks = response.data;
      })
      .catch(function (e) {
        this.error = e;
      });
    axios
      .get("/api/tasks/byStatus/?status=true")
      .then(response => {
        this.completedTasks = response.data;
      })
      .catch(function (e) {
        this.error = e;
      });
  },


  methods: {
    handleInput(event) {
      this.valueInput = event.target.value;
    },

    addTask() {
      if (this.valueInput === "") { return };
      axios
        .post("/api/tasks/", {
          text: this.valueInput,
          isPerformed: false
        })
        .then(response => {
          this.tasks.push(response.data)
        })
        .catch(function (e) {
          this.error = e;
        });
      this.valueInput = "";
    },

    // handles clicking checkboxes
    doCheck(type, task, index) {
      if (type === 'need') {
        axios
          .put("/api/tasks/" + task.id, {
            text: task.text,
            isPerformed: true
          })
          .then(response => {
            this.completedTasks.push(task)
            this.tasks.splice(index, 1)
          })
          .catch(function (e) {
            this.error = e;
          });
      }
      else {
        axios
          .put("/api/tasks/" + task.id, {
            text: task.text,
            isPerformed: false
          })
          .then(response => {
            this.tasks.push(task)
            this.completedTasks.splice(index, 1)
          })
          .catch(function (e) {
            this.error = e;
          });
      }
    },


    removeTask(task, index, type) {
      axios
        .delete("/api/tasks/" + task.id)
        .then(response => {
          const toDoList = type === 'complete' ? this.completedTasks : this.tasks;
          toDoList.splice(index, 1);
        })

    },
  },
}
).mount('#app');