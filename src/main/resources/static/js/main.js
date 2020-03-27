const URL = "http://localhost:8080";
const showErrorMsg = false;
const errorMsg = "Sorry we coundn't connect to server";

function keyUp(event) {
  let value = $("#searchBox")[0].value;

  if (value === "") $(".suggestions").hide();
  else $(".suggestions").show();

  if (event.key !== "Enter" && value !== "") {
    autocompleteWord(value);
  }

  // if pressed enter
  if (event.key === "Enter" && value !== "") {
    searchWord(value);
    $(".suggestions").hide();
  }
}

function searchThisKey(event) {
  let value = event.target.innerText;
  $("#searchBox")[0].value = value;
  searchWord(value);
  $(".suggestions").hide();
}

function searchWord(value) {
  // call search API
  axios.get(URL + "/search/" + value).then(response => {
    if (response && response.status === 200) {
      console.log("data: " + response);
      let tag = "";
      response.data.forEach(element => {
        tag +=
          ' <div class="result"><div class="title">' +
          element.title +
          '</div><div class="desc">' +
          element.description +
          '</div><div class="link"><a href="' +
          element.url +
          '">' +
          element.url +
          "</a></div></div>";
      });
      $(".content").empty();
      $(".content").append(tag);
    } else showErrorMsg = true;
  });
}

function autocompleteWord(value) {
  // call autocomplete API
  axios.get(URL + "/autocomplete/" + value).then(response => {
    if (response && response.status === 200) {
      let tag = "";
      response.data.forEach(element => {
        tag +=
          '<div class="suggestion" onclick="searchThisKey(event)">' +
          element +
          "</div>";
      });
      $(".suggestions").empty();
      $(".suggestions").append(tag);
    } else showErrorMsg = true;
  });
}
