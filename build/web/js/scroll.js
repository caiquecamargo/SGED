const menuItems = document.querySelectorAll('.nav a[href^="#"]');

function getScrollTopByHref(element) {
  const id = element.getAttribute('href');
  return document.querySelector(id).offsetTop;
}

function scrollToPosition(to) {
  // Caso queira o nativo apenas
  const content = document.querySelectorAll('.content');

  content[0].scroll({
    top: to,
    behavior: "smooth",
  })
}

function scrollToIdOnClick(event) {
  event.preventDefault();
  const to = getScrollTopByHref(event.currentTarget) - 110;
  scrollToPosition(to);
}

menuItems.forEach(item => {
  item.addEventListener('click', scrollToIdOnClick);
});