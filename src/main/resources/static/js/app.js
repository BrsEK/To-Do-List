Vue.createApp({
  data() {
    return {
      valueInput: '',
      needDoList: [],
      completeList: [],
      tasks: [],
      task: {
        text: null,
        isPerformed: false
      }
    };
  },
  mounted: function () {
    axios
      .get("/api/tasks/")
      .then(response => {
        this.tasks = response.data;
      })
      .catch(function (e) {
        this.error = e;
      });
  },

  methods: {
    handleInput (event) {
        this.valueInput = event.target.value;
    },

    addTask() {
      if(this.valueInput===""){ return };
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
    }
  },
}
).mount('#app');