const menuBtn = document.querySelector('.menuButton');
const showit = document.querySelector('.mobileNav');
let menuOpen = false;
menuBtn.addEventListener('click', () =>{
  if(!menuOpen){
    showit.classList.add('showIt');
    menuBtn.classList.add('open');
    menuOpen = true;
  }else {
    menuBtn.classList.remove('open');
    showit.classList.remove('showIt');
    menuOpen = false;
  }
});
