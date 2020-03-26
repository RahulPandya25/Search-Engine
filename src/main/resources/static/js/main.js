const URL = "http://localhost:8080";
const showErrorMsg = false;
const errorMsg = "Sorry we coundn't connect to server";

function search(event) {
  let value = $("#searchBox").value;

  // if pressed enter
  if (event.code === "Enter") {
    // call our API
    axios.get(URL + "/search/+" + value).then(response => {
      if (response && response.status === 200)
        console.log("data: " + response.data);
      else showErrorMsg = true;
    });
  }
}
