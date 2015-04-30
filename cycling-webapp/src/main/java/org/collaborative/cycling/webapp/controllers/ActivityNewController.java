package org.collaborative.cycling.webapp.controllers;

import org.collaborative.cycling.models.Coordinates;
import org.collaborative.cycling.models.User;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


class HelpRequest {
    public User userInfo;
    public Coordinates lastLocation;
    public String text;
}

@Path("/activity")
public class ActivityNewController {

    private static int index = 0;

    public ActivityNewController() {
    }

    @GET
    @Path("{activityId}/help")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public List<? extends HelpRequest> getUsersForMap() {
        return Arrays.asList(
                new HelpRequest() {{
                    userInfo = new User("1@1.com", "");
                    lastLocation = track.get(10 + index);
                    text = "BLA1";
                }},
                new HelpRequest() {{
                    userInfo = new User("1@1.com", "");
                    lastLocation = track.get(10 + index);
                    text = "BLA2";
                }},
                new HelpRequest() {{
                    userInfo = new User("1@1.com", "");
                    lastLocation = track.get(10 + index);
                    text = "BLA3";
                }}
        );
    }

    @GET
    @Path("{activityId}/getUsers")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public List<? extends PEUserMapDetails> getUsersForMap(@PathParam("activityId") String activityId,
                                                           @QueryParam("group") boolean getFromGroup,
                                                           @QueryParam("nearby") boolean getNearby) {


        List<PEUserMapDetails> group = Arrays.asList(

                new PEUserMapDetails() {{
                    userInfo = new User("1@1.com", "");
                    userLocation = track.get(10 + index);
                    isGroup = true;
                }},
                new PEUserMapDetails() {{
                    userInfo = new User("2@1.com", "");
                    userLocation = track.get(100 + index);
                    isGroup = true;
                }},
                new PEUserMapDetails() {{
                    userInfo = new User("3@1.com", "");
                    userLocation = track.get(110 + index);
                    isGroup = true;
                }}
        );

        List<PEUserMapDetails> arround = Arrays.asList(
                new PEUserMapDetails() {{
                    userInfo = new User("4@1.com", "");
                    userLocation = track.get(11 + index);
                    isGroup = false;
                }},
                new PEUserMapDetails() {{
                    userInfo = new User("5@1.com", "");
                    userLocation = track.get(50 + index);
                    isGroup = false;
                }}
        );

        List<PEUserMapDetails> result = new ArrayList<>();

        if (getFromGroup) {
            result.addAll(group);
        }

        if (getNearby) {
            result.addAll(arround);
        }

        index++;
        return result;
    }


    public static final List<Coordinates> track = Arrays.asList(
            new Coordinates(44.51742, 26.08507),
            new Coordinates(44.51732, 26.08614),
            new Coordinates(44.51724, 26.08656),
            new Coordinates(44.51695, 26.08666),
            new Coordinates(44.5165, 26.09053),
            new Coordinates(44.51581, 26.0972),
            new Coordinates(44.51549, 26.10058),
            new Coordinates(44.51528, 26.10253),
            new Coordinates(44.5153, 26.10291),
            new Coordinates(44.51612, 26.10301),
            new Coordinates(44.51666, 26.10283),
            new Coordinates(44.51722, 26.10251),
            new Coordinates(44.51785, 26.10265),
            new Coordinates(44.51975, 26.10426),
            new Coordinates(44.52017, 26.1044),
            new Coordinates(44.52027, 26.10369),
            new Coordinates(44.52041, 26.10141),
            new Coordinates(44.52167, 26.09294),
            new Coordinates(44.52226, 26.08903),
            new Coordinates(44.52238, 26.08789),
            new Coordinates(44.52266, 26.08602),
            new Coordinates(44.52312, 26.08175),
            new Coordinates(44.52973, 26.08311),
            new Coordinates(44.52912, 26.08697),
            new Coordinates(44.53502, 26.08879),
            new Coordinates(44.53625, 26.08731),
            new Coordinates(44.53692, 26.08693),
            new Coordinates(44.53716, 26.08691),
            new Coordinates(44.53749, 26.08708),
            new Coordinates(44.53757, 26.08733),
            new Coordinates(44.53768, 26.08842),
            new Coordinates(44.53782, 26.08953),
            new Coordinates(44.53809, 26.09049),
            new Coordinates(44.53831, 26.09252),
            new Coordinates(44.5383, 26.09283),
            new Coordinates(44.53892, 26.09735),
            new Coordinates(44.53912, 26.09782),
            new Coordinates(44.5394, 26.09786),
            new Coordinates(44.53949, 26.09859),
            new Coordinates(44.53959, 26.10008),
            new Coordinates(44.53953, 26.10442),
            new Coordinates(44.54521, 26.1061),
            new Coordinates(44.54545, 26.10612),
            new Coordinates(44.54882, 26.10717),
            new Coordinates(44.54944, 26.10771),
            new Coordinates(44.55029, 26.10869),
            new Coordinates(44.55054, 26.10878),
            new Coordinates(44.55077, 26.10799),
            new Coordinates(44.55148, 26.1079),
            new Coordinates(44.55202, 26.10735),
            new Coordinates(44.55222, 26.10695),
            new Coordinates(44.55261, 26.10655),
            new Coordinates(44.55291, 26.10651),
            new Coordinates(44.55299, 26.10658),
            new Coordinates(44.55404, 26.10713),
            new Coordinates(44.55718, 26.10873),
            new Coordinates(44.56022, 26.11016),
            new Coordinates(44.5614, 26.11067),
            new Coordinates(44.56179, 26.11083),
            new Coordinates(44.56199, 26.11086),
            new Coordinates(44.5621, 26.11073),
            new Coordinates(44.56284, 26.11166),
            new Coordinates(44.56336, 26.11258),
            new Coordinates(44.56343, 26.1128),
            new Coordinates(44.56342, 26.1132),
            new Coordinates(44.56307, 26.11532),
            new Coordinates(44.56322, 26.11646),
            new Coordinates(44.56336, 26.11694),
            new Coordinates(44.56357, 26.11707),
            new Coordinates(44.56529, 26.11605),
            new Coordinates(44.56617, 26.11667),
            new Coordinates(44.56664, 26.12339),
            new Coordinates(44.56692, 26.12649),
            new Coordinates(44.5675, 26.13123),
            new Coordinates(44.56788, 26.1343),
            new Coordinates(44.56794, 26.13446),
            new Coordinates(44.5683, 26.13443),
            new Coordinates(44.56878, 26.14093),
            new Coordinates(44.56671, 26.14128),
            new Coordinates(44.5683, 26.15834),
            new Coordinates(44.57266, 26.1577),
            new Coordinates(44.5731, 26.16446),
            new Coordinates(44.57328, 26.16758),
            new Coordinates(44.57372, 26.1747),
            new Coordinates(44.58118, 26.17182),
            new Coordinates(44.58145, 26.17159),
            new Coordinates(44.58177, 26.17155),
            new Coordinates(44.58799, 26.16903),
            new Coordinates(44.588, 26.16923),
            new Coordinates(44.59119, 26.16807),
            new Coordinates(44.5996, 26.16495),
            new Coordinates(44.60076, 26.16509),
            new Coordinates(44.601, 26.16507),
            new Coordinates(44.60621, 26.16615),
            new Coordinates(44.61553, 26.16831),
            new Coordinates(44.62697, 26.17091),
            new Coordinates(44.62864, 26.17125),
            new Coordinates(44.62867, 26.17114),
            new Coordinates(44.62873, 26.17106),
            new Coordinates(44.62885, 26.17101),
            new Coordinates(44.62895, 26.17104),
            new Coordinates(44.62908, 26.17098),
            new Coordinates(44.62942, 26.17106),
            new Coordinates(44.62956, 26.17091),
            new Coordinates(44.62972, 26.17051),
            new Coordinates(44.63003, 26.17008),
            new Coordinates(44.63019, 26.16997),
            new Coordinates(44.63035, 26.17006),
            new Coordinates(44.63045, 26.17049),
            new Coordinates(44.63052, 26.17127),
            new Coordinates(44.6306, 26.17152),
            new Coordinates(44.63066, 26.17169),
            new Coordinates(44.63064, 26.17213),
            new Coordinates(44.63063, 26.17262),
            new Coordinates(44.63063, 26.17308),
            new Coordinates(44.63061, 26.17352),
            new Coordinates(44.6306, 26.17392),
            new Coordinates(44.6306, 26.17418),
            new Coordinates(44.63076, 26.17455),
            new Coordinates(44.63198, 26.17427),
            new Coordinates(44.63186, 26.17384),
            new Coordinates(44.63176, 26.17311),
            new Coordinates(44.63178, 26.1722),
            new Coordinates(44.63186, 26.17149),
            new Coordinates(44.63186, 26.17085),
            new Coordinates(44.63202, 26.16987),
            new Coordinates(44.6323, 26.16903),
            new Coordinates(44.63247, 26.16815),
            new Coordinates(44.63269, 26.16765),
            new Coordinates(44.63302, 26.16646),
            new Coordinates(44.63324, 26.16563),
            new Coordinates(44.63333, 26.1646),
            new Coordinates(44.63354, 26.16392),
            new Coordinates(44.63373, 26.16346),
            new Coordinates(44.63391, 26.16274),
            new Coordinates(44.63453, 26.16212),
            new Coordinates(44.63453, 26.16191),
            new Coordinates(44.63448, 26.16172),
            new Coordinates(44.63419, 26.16119),
            new Coordinates(44.63389, 26.16045),
            new Coordinates(44.63363, 26.1601),
            new Coordinates(44.6334, 26.15993),
            new Coordinates(44.63322, 26.1595),
            new Coordinates(44.63281, 26.15917),
            new Coordinates(44.6324, 26.15874),
            new Coordinates(44.63191, 26.15869),
            new Coordinates(44.63148, 26.15834),
            new Coordinates(44.63065, 26.15739),
            new Coordinates(44.62969, 26.1558),
            new Coordinates(44.62942, 26.15505),
            new Coordinates(44.6293, 26.15481),
            new Coordinates(44.62904, 26.15371),
            new Coordinates(44.62912, 26.15293),
            new Coordinates(44.62921, 26.15214),
            new Coordinates(44.62936, 26.15167),
            new Coordinates(44.62962, 26.15149),
            new Coordinates(44.6298, 26.151),
            new Coordinates(44.6299, 26.15087),
            new Coordinates(44.62997, 26.14998),
            new Coordinates(44.63017, 26.14892),
            new Coordinates(44.63008, 26.14858),
            new Coordinates(44.62966, 26.14785),
            new Coordinates(44.6294, 26.14706),
            new Coordinates(44.62955, 26.14684),
            new Coordinates(44.62991, 26.14579),
            new Coordinates(44.63012, 26.14542),
            new Coordinates(44.63037, 26.14519),
            new Coordinates(44.63913, 26.13991),
            new Coordinates(44.63753, 26.13479),
            new Coordinates(44.64532, 26.12933),
            new Coordinates(44.64774, 26.12766),
            new Coordinates(44.64769, 26.1269),
            new Coordinates(44.64729, 26.12627),
            new Coordinates(44.6464, 26.12516),
            new Coordinates(44.64577, 26.12481),
            new Coordinates(44.64564, 26.12398),
            new Coordinates(44.64539, 26.12366),
            new Coordinates(44.64493, 26.12271),
            new Coordinates(44.64467, 26.12241),
            new Coordinates(44.64396, 26.12216),
            new Coordinates(44.64375, 26.12095),
            new Coordinates(44.64326, 26.11946),
            new Coordinates(44.64203, 26.11373),
            new Coordinates(44.64231, 26.11376),
            new Coordinates(44.64276, 26.11389),
            new Coordinates(44.64346, 26.11418),
            new Coordinates(44.64377, 26.11416),
            new Coordinates(44.64399, 26.11398),
            new Coordinates(44.64426, 26.11359),
            new Coordinates(44.64452, 26.11352),
            new Coordinates(44.64531, 26.11376),
            new Coordinates(44.64503, 26.10989),
            new Coordinates(44.64489, 26.10671),
            new Coordinates(44.6449, 26.10351),
            new Coordinates(44.64495, 26.10076),
            new Coordinates(44.64495, 26.09875),
            new Coordinates(44.64915, 26.09685),
            new Coordinates(44.65361, 26.09504),
            new Coordinates(44.65767, 26.09385),
            new Coordinates(44.65827, 26.09859),
            new Coordinates(44.65932, 26.10559),
            new Coordinates(44.66041, 26.11303),
            new Coordinates(44.66197, 26.12051),
            new Coordinates(44.66288, 26.12482),
            new Coordinates(44.66373, 26.12891),
            new Coordinates(44.66457, 26.1319),
            new Coordinates(44.66557, 26.13637),
            new Coordinates(44.66583, 26.13721),
            new Coordinates(44.66609, 26.13805),
            new Coordinates(44.66615, 26.13856),
            new Coordinates(44.66632, 26.13897),
            new Coordinates(44.66627, 26.13943),
            new Coordinates(44.66617, 26.13967),
            new Coordinates(44.66586, 26.14005),
            new Coordinates(44.66561, 26.14058),
            new Coordinates(44.66538, 26.14085),
            new Coordinates(44.66495, 26.14107),
            new Coordinates(44.66237, 26.14302),
            new Coordinates(44.65914, 26.14549),
            new Coordinates(44.65919, 26.14678),
            new Coordinates(44.65922, 26.14732),
            new Coordinates(44.65916, 26.14779),
            new Coordinates(44.65917, 26.14815),
            new Coordinates(44.65921, 26.14864),
            new Coordinates(44.65932, 26.14902),
            new Coordinates(44.65935, 26.14919),
            new Coordinates(44.65919, 26.14935),
            new Coordinates(44.65898, 26.14933),
            new Coordinates(44.6587, 26.14939),
            new Coordinates(44.65844, 26.1496),
            new Coordinates(44.65834, 26.15067),
            new Coordinates(44.65856, 26.15153),
            new Coordinates(44.65894, 26.15201),
            new Coordinates(44.65941, 26.1524),
            new Coordinates(44.66009, 26.15317),
            new Coordinates(44.66039, 26.15367),
            new Coordinates(44.66113, 26.15566),
            new Coordinates(44.66116, 26.15614),
            new Coordinates(44.66113, 26.15659),
            new Coordinates(44.66116, 26.15697),
            new Coordinates(44.66103, 26.15743),
            new Coordinates(44.66048, 26.15836),
            new Coordinates(44.66038, 26.15861),
            new Coordinates(44.66034, 26.15909),
            new Coordinates(44.66049, 26.15967),
            new Coordinates(44.66055, 26.16042),
            new Coordinates(44.66082, 26.16117),
            new Coordinates(44.6613, 26.16233),
            new Coordinates(44.66185, 26.16363),
            new Coordinates(44.66213, 26.16403),
            new Coordinates(44.66146, 26.16429),
            new Coordinates(44.66112, 26.16458),
            new Coordinates(44.66033, 26.16515),
            new Coordinates(44.65931, 26.16549),
            new Coordinates(44.65864, 26.16584),
            new Coordinates(44.6584, 26.1661),
            new Coordinates(44.65765, 26.16656),
            new Coordinates(44.65754, 26.16679),
            new Coordinates(44.65762, 26.16687),
            new Coordinates(44.65937, 26.16606),
            new Coordinates(44.65992, 26.16589),
            new Coordinates(44.6604, 26.16559),
            new Coordinates(44.66118, 26.16548),
            new Coordinates(44.66127, 26.16527),
            new Coordinates(44.66144, 26.1651),
            new Coordinates(44.66184, 26.16494),
            new Coordinates(44.66204, 26.16497),
            new Coordinates(44.66229, 26.16536),
            new Coordinates(44.66311, 26.16618),
            new Coordinates(44.66385, 26.16706),
            new Coordinates(44.66405, 26.16733),
            new Coordinates(44.66457, 26.16858),
            new Coordinates(44.66462, 26.16919),
            new Coordinates(44.66472, 26.16952),
            new Coordinates(44.66501, 26.17019),
            new Coordinates(44.66537, 26.17048),
            new Coordinates(44.66557, 26.17037),
            new Coordinates(44.66722, 26.16857),
            new Coordinates(44.66749, 26.16812),
            new Coordinates(44.66775, 26.16794),
            new Coordinates(44.6682, 26.16776),
            new Coordinates(44.66832, 26.16754),
            new Coordinates(44.66809, 26.16597),
            new Coordinates(44.66839, 26.16542),
            new Coordinates(44.67031, 26.16464),
            new Coordinates(44.67071, 26.16464),
            new Coordinates(44.67125, 26.16528),
            new Coordinates(44.67151, 26.1662),
            new Coordinates(44.67218, 26.16776),
            new Coordinates(44.67353, 26.16922),
            new Coordinates(44.67466, 26.17125),
            new Coordinates(44.67695, 26.17526),
            new Coordinates(44.67736, 26.17664),
            new Coordinates(44.67831, 26.17869),
            new Coordinates(44.67891, 26.17951),
            new Coordinates(44.68212, 26.17591),
            new Coordinates(44.68666, 26.18361),
            new Coordinates(44.68109, 26.19039),
            new Coordinates(44.6849, 26.19658),
            new Coordinates(44.69287, 26.21006),
            new Coordinates(44.69522, 26.21363),
            new Coordinates(44.69532, 26.2138),
            new Coordinates(44.69542, 26.21384),
            new Coordinates(44.6958, 26.21332),
            new Coordinates(44.69631, 26.21244),
            new Coordinates(44.69709, 26.21178),
            new Coordinates(44.69845, 26.21054),
            new Coordinates(44.70514, 26.20808),
            new Coordinates(44.70676, 26.2126),
            new Coordinates(44.70819, 26.21586),
            new Coordinates(44.70846, 26.21572),
            new Coordinates(44.70965, 26.2162),
            new Coordinates(44.71032, 26.21676),
            new Coordinates(44.71179, 26.21479),
            new Coordinates(44.71276, 26.21284),
            new Coordinates(44.71337, 26.21185),
            new Coordinates(44.71396, 26.21148),
            new Coordinates(44.71461, 26.21045),
            new Coordinates(44.71473, 26.21002),
            new Coordinates(44.71467, 26.20914),
            new Coordinates(44.71553, 26.20875),
            new Coordinates(44.71664, 26.20795),
            new Coordinates(44.7172, 26.20879),
            new Coordinates(44.71866, 26.20844),
            new Coordinates(44.71945, 26.20769),
            new Coordinates(44.71948, 26.20676),
            new Coordinates(44.72024, 26.20628),
            new Coordinates(44.7203, 26.20585),
            new Coordinates(44.72186, 26.20505),
            new Coordinates(44.7226, 26.20484),
            new Coordinates(44.72293, 26.20515),
            new Coordinates(44.72335, 26.206),
            new Coordinates(44.72354, 26.2061),
            new Coordinates(44.72361, 26.20644),
            new Coordinates(44.72388, 26.20711),
            new Coordinates(44.72427, 26.20743),
            new Coordinates(44.72438, 26.2074),
            new Coordinates(44.72508, 26.20655),
            new Coordinates(44.72546, 26.20633),
            new Coordinates(44.72564, 26.20507),
            new Coordinates(44.72579, 26.20361),
            new Coordinates(44.72604, 26.20214),
            new Coordinates(44.72592, 26.20068),
            new Coordinates(44.72522, 26.19965),
            new Coordinates(44.72512, 26.19962),
            new Coordinates(44.72506, 26.19951),
            new Coordinates(44.72506, 26.19927),
            new Coordinates(44.7251, 26.1991),
            new Coordinates(44.72493, 26.19454),
            new Coordinates(44.72499, 26.19311),
            new Coordinates(44.72498, 26.19124),
            new Coordinates(44.72501, 26.19098),
            new Coordinates(44.72514, 26.19082),
            new Coordinates(44.72514, 26.19052),
            new Coordinates(44.72493, 26.1904),
            new Coordinates(44.72473, 26.19018)
    );


}


