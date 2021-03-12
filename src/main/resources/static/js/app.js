Vue.createApp({
  data() {
    return {
      valueInput: '',
      needDoList: [],
      completeList: [],
      tasks: [],
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
}
).mount('#app');