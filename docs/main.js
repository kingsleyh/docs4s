function toArray(xs) {
    return Array.prototype.slice.call(xs);
}

function filterTocType(category) {
    nameFilter.value = category;
    filterToc();
}

function filterToc() {
    var f = filterElement.bind(null, nameFilter.value);
    funcs.forEach(f);
}

function filterElement(nameFilter, elem) {
    var name = elem.getAttribute('data-name');
    var category = elem.getAttribute('data-category');
    var matches = strIn(nameFilter, name) || nameFilter.toLowerCase() === category.toLowerCase();
    elem.style.display = matches ? '' : 'none';
}

function findFirst(funcs) {
    var found;
    funcs.forEach(function(fu) {
        if (!found && fu.offsetParent) found = fu;
    });

    return found;
}

function gotoFirst(e) {
    if (!e.detail) {
        return;
    }

    var func = findFirst(funcs);
    if (func) {
        console.log(func);

        var onHashChange = function() {
            e.target.focus();
            window.removeEventListener('hashchange', onHashChange);
        };

        // Hash change blurs input, put focus back to input
        window.addEventListener('hashchange', onHashChange);
        window.location.hash = func.getAttribute('data-name');
    }
}

function strIn(a, b) {
    a = a.toLowerCase();
    b = b.toLowerCase();
    return b.indexOf(a) >= 0;
}

function scrollToTop() {
    var main = document.querySelector('main');
    main.scrollTop = 0;
}

function tryToggleCollapse(elem) {
    var sel = elem && elem.getAttribute('data-collapser');
    if (!sel) { return; }
    elem
        .classList
        .toggle('open');
    document
        .querySelector(sel)
        .classList
        .toggle('in');
}

function isTopLink(elem) {
    return elem.getAttribute('href') === '#';
}

function isAnchorLink(elem) {
    return elem.tagName === 'A' && elem.getAttribute('href').charAt(0) === '#';
}

function closeNav() {
    document.getElementById('open-nav').checked = false;
}

function dispatchEvent(event) {
    var target = event.target;
    var parent = target.parentNode;
    var category = target.getAttribute('data-category');

    if (isAnchorLink(target)) {
        closeNav();
    }
    if (category) {
        filterTocType(category);
    }
    if (isTopLink(target)) {
        scrollToTop(target);
    } else {
        tryToggleCollapse(target);
        tryToggleCollapse(parent);
    }
}

function keypress(e) {
    if (e.which == 13) {
        e.target.dispatchEvent(new window.CustomEvent('enter', {
            detail: e.target.value
        }));
    }
}

var nameFilter = document.getElementById('name-filter');
var funcs = toArray(document.querySelectorAll('.toc .func'));
filterToc();

document.body.addEventListener('click', dispatchEvent, false);
nameFilter.addEventListener('input', filterToc, false);
nameFilter.addEventListener('keypress', keypress, false);
nameFilter.addEventListener('enter', gotoFirst);

document.body.addEventListener('click', function(event) {
    if (event.target.className.split(' ').indexOf('toggle-params') >= 0) {
        var expanded = event.target.parentNode.getAttribute('data-expanded');
        event.target.parentNode.setAttribute(
            'data-expanded',
            expanded === 'true' ? 'false' : 'true'
        );
    }
}, false);

var currentFocus = window.location.hash;
window.location.hash = "";
window.location.hash = currentFocus;