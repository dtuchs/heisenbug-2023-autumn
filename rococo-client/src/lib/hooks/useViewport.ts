let intersectionObserver: IntersectionObserver;

const initObserver = () => {
    intersectionObserver = new IntersectionObserver((entries) => {
    entries.forEach((entry) => {
        const eventName = entry.isIntersecting ? "viewportenter" : "viewportleave";
        entry.target.dispatchEvent(new CustomEvent(eventName));
        });
    })
};

export default function viewport(element: Element) {
    if(!intersectionObserver) {
        initObserver();
    }
    intersectionObserver.observe(element);
    return {
        destroy() {
            intersectionObserver.unobserve(element);
        }
    }
}
