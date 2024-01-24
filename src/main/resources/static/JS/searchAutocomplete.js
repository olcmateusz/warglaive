let availableKeywords = [
  'HTML',
  'CSS'
];

const resultsBox = document.querySelector(".result-box");
const inputBox = document.getElementById("input-box");
const characterId = document.getElementById("character-id");

inputBox.onkeyup = function(){
  let result = [];
  let input = inputBox.value;
  if(input.length){
    result = characters.filter((character) => {
      return character.name.toLowerCase().includes(input.toLowerCase());
    });
  }
  display(result);
  if(!result.length){
    resultsBox.innerHTML = '';
  }
}

function display(result){
  const content = result.map((character) => {
    return "<li onclick=selectInput(this" + "," + character.id + ")>" + character.name + " - " + character.realmName + "</li>";
  });
  resultsBox.innerHTML = "<ul>" + content.join('') + "</ul>";
}

function selectInput(list, id){
  const form = document.getElementById("character-form");
  inputBox.value = list.innerHTML;
  characterId.value = id;
  console.log(id);
  console.log(document.getElementById("character-id").value);
  const formDataObject = {};
  new FormData(form).forEach((value, key) => {
    formDataObject[key] = value;
  });
  console.log("Form data before submission:", formDataObject);
  console.log("Form submission method:", form.method);
  resultsBox.innerHTML = '';
  event.preventDefault();
  form.method = "post";
  console.log("Form data before submission2:", new FormData(form)); // Log the form data
  form.submit();
}
