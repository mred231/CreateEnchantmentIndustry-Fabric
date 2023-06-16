package plus.dragons.createenchantmentindustry.content.contraptions.fluids.ink;

public interface InkRenderingCamera {

    boolean isInInk();

	// FIXME
    /*static void handleInkFogColor(ViewportEvent.ComputeFogColor event) {
        if (((InkRenderingCamera) event.getCamera()).isInInk()) {
            event.setRed(0);
            event.setGreen(0);
            event.setBlue(0);
        }
    }*/

}
