const mediaQuery = window.matchMedia('(min-width: 1350px)')
const navbar = document.querySelector('.navbar');
const logo = document.querySelector('.logo');
const logobig = document.querySelector('.logoBig');
const small = document.querySelector('.logoSmall');
const navi = document.querySelector('.siteNavLogo');
window.onscroll = () => {
    if (window.scrollY > 10 && mediaQuery.matches) {
        navbar.classList.add('navActive');
        logobig.classList.add('bighidden');
        small.classList.add('displayed');
        logo.classList.add('shrink');
        navi.classList.add('shrinK');
    } else {
        navbar.classList.remove('navActive');
        logobig.classList.remove('bighidden');
        small.classList.remove('displayed');
        logo.classList.remove('shrink');
        navi.classList.remove('shrinK');
    }
};
