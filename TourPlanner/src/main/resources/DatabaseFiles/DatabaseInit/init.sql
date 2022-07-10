CREATE TABLE IF NOT EXISTS "Tour" (
    "id" VARCHAR(255) NOT NULL,
    "tourName" VARCHAR(255) NOT NULL,
    "description" VARCHAR(255) NOT NULL,
    "startDest" VARCHAR(255) NOT NULL,
    "endDest" VARCHAR(255) NOT NULL,
    "transportType" VARCHAR(255) NOT NULL,
    "dist" REAL NOT NULL,
    "estTime" VARCHAR(255) NOT NULL,
    "info" VARCHAR(255) NOT NULL,
    CONSTRAINT tour_pkey PRIMARY KEY ("id")
);


CREATE TABLE IF NOT EXISTS "TourLog" (
    "id" VARCHAR(255) NOT NULL,
    "tourId" VARCHAR(255) NOT NULL,
    "date" VARCHAR(255) NOT NULL,
    "commentary" VARCHAR(255) NOT NULL,
    "totalTime" VARCHAR(255) NOT NULL,
     CONSTRAINT tourLog_pkey PRIMARY KEY ("id")
);


ALTER TABLE "TourLog" ADD CONSTRAINT tourLog_tourId_fkey FOREIGN KEY ("tourId") REFERENCES "Tour" ("id");
