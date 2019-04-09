import org.liballeg.*
import kotlin.system.exitProcess
import kotlinx.cinterop.*
import platform.posix.*

fun main(args: Array<String>) {
    if (!al_install_system(ALLEGRO_VERSION_INT, staticCFunction(::atexit))) {
        exitProcess(-1)
    }
    if(!al_install_keyboard()) {
      exitProcess(-1)
   }

   al_init_font_addon()
   val font = al_create_builtin_font()

    val display = al_create_display(640, 480) ?: exitProcess(-1)

    val event_queue = al_create_event_queue()
    if (event_queue == null) {
        al_destroy_display(display)
        exitProcess(-1)
    }

    al_register_event_source(event_queue, al_get_display_event_source(display))
    al_register_event_source(event_queue, al_get_keyboard_event_source())

    al_clear_to_color(al_map_rgb(255, 255, 255))
    al_flip_display()

    var shallExit = false
    while (!shallExit) {
        memScoped {
            val ev: ALLEGRO_EVENT = alloc()
            val timeout: ALLEGRO_TIMEOUT = alloc()
            al_init_timeout(timeout.ptr, 0.06);

            if(al_wait_for_event_until(event_queue, ev.ptr, timeout.ptr)) {
                when (ev.type) {
                    ALLEGRO_EVENT_DISPLAY_CLOSE -> shallExit = true
                    ALLEGRO_EVENT_KEY_UP -> {
                        if (ev.keyboard.keycode == ALLEGRO_KEY_ESCAPE.toInt()) {
                            shallExit = true
                        }
                    }
                }
            }
        }

        al_clear_to_color(al_map_rgb(255, 255, 255))
        al_draw_text(font, al_map_rgb(0, 0, 0), 640/2f, 480/2f, ALLEGRO_ALIGN_CENTRE, "Press Esc to quit")
        al_flip_display()

        if (shallExit) break
    }

    al_destroy_display(display)
    al_destroy_event_queue(event_queue)
}
